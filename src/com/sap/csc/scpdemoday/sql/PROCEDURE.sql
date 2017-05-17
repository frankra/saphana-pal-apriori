--------Demo
-- Test PAL Libraray
--SELECT * FROM "SYS"."AFL_AREAS" WHERE AREA_NAME = 'AFLPAL';
--SELECT * FROM "SYS"."AFL_PACKAGES" WHERE AREA_NAME = 'AFLPAL';
--SELECT * FROM "SYS"."AFL_FUNCTIONS" WHERE AREA_NAME = 'AFLPAL';

-- Create Table
DROP TABLE TWEET;
CREATE COLUMN TABLE TWEET ("ID" BIGINT, "DATE" TIMESTAMP, "CONTENT" NVARCHAR(255), PRIMARY KEY ("ID"));

DROP FULLTEXT INDEX TWEET_FULLTEXT_INDEX;
CREATE FULLTEXT INDEX TWEET_FULLTEXT_INDEX ON TWEET("CONTENT") TEXT ANALYSIS ON CONFIGURATION 'EXTRACTION_CORE';
SELECT * FROM "$TA_TWEET_FULLTEXT_INDEX";

--Create the View
DROP VIEW "TRANSACTIONS_VIEW";
CREATE VIEW "TRANSACTIONS_VIEW" AS
SELECT ID, TA_TOKEN as TOKEN FROM "$TA_TWEET_FULLTEXT_INDEX"
WHERE TA_TYPE = 'SOCIAL_MEDIA/TOPIC_TWITTER';

/* CREATE TABLE TYPE FOR MY INPUT DATA */
DROP TYPE PAL_DATA_T;
CREATE TYPE PAL_DATA_T AS TABLE(
"ID" INT,
"TOKEN" VARCHAR(100)
);

/* CREATE TABLE TYPE FOR THE OUTPUT TABLE */
DROP TYPE PAL_RESULT_T;
CREATE TYPE PAL_RESULT_T AS TABLE(
"PRERULE" VARCHAR(500),
"POSTRULE" VARCHAR(500),
"SUPPORT" DOUBLE,
"CONFIDENCE" DOUBLE,
"LIFT" DOUBLE
);

/* CREATE TABLE TYPE FOR THE OUTPUT PMML MODEL */
DROP TYPE PAL_PMMLMODEL_T;
CREATE TYPE PAL_PMMLMODEL_T AS TABLE(
"ID" INT,
"PMMLMODEL" VARCHAR(5000)
);


/* CREATE TABLE TYPE FOR THE TABLE THAT WILL CONTAIN THE INPUT PARAMETERS */
DROP TYPE PAL_CONTROL_T;
CREATE TYPE PAL_CONTROL_T AS TABLE(
"NAME" VARCHAR (50),
"INTARGS" INTEGER,
"DOUBLEARGS" DOUBLE,
"STRINGARGS" VARCHAR (100)
);

DROP TABLE APRIORI_RESULT;
CREATE COLUMN TABLE APRIORI_RESULT(
"PRERULE" VARCHAR(500),
"POSTRULE" VARCHAR(500),
"SUPPORT" Double,
"CONFIDENCE" Double,
"LIFT" DOUBLE
);

/* CREATE THE OUTPUT TABLE WITH THE PMML MODEL */
DROP TABLE APRIORI_PMML_MODEL;
CREATE COLUMN TABLE APRIORI_PMML_MODEL (
"ID" INT,
"PMMLMODEL" VARCHAR(5000)
);

/* CREATE TABLE THAT WILL POINT TO THE DIFFERENT TYPES I'M USING TO RUN THE ALGORITHM */
DROP TABLE PDATA;
CREATE COLUMN TABLE PDATA(
"POSITION" INT,
"SCHEMA_NAME" NVARCHAR(256),
"TYPE_NAME" NVARCHAR(256),
"PARAMETER_TYPE" VARCHAR(7)
);
INSERT INTO PDATA VALUES (1, CURRENT_USER, 'PAL_DATA_T', 'IN');
INSERT INTO PDATA VALUES (2, CURRENT_USER, 'PAL_CONTROL_T', 'IN');
INSERT INTO PDATA VALUES (3, CURRENT_USER, 'PAL_RESULT_T', 'OUT');
INSERT INTO PDATA VALUES (4, CURRENT_USER, 'PAL_PMMLMODEL_T', 'OUT');

CALL SYS.AFLLANG_WRAPPER_PROCEDURE_DROP('SYSTEM', 'PAL_APRIORI_RULE');
CALL SYS.AFLLANG_WRAPPER_PROCEDURE_CREATE('AFLPAL', 'APRIORIRULE', 'SYSTEM', 'PAL_APRIORI_RULE', PDATA);

/* CREATE TABLE THAT WILL CONTAIN THE INPUT PARAMETERS */
DROP TABLE APRIORI_PROCEDURE_CONFIGURATION;
CREATE COLUMN TABLE APRIORI_PROCEDURE_CONFIGURATION(
"NAME" VARCHAR (50),
"INTARGS" INTEGER,
"DOUBLEARGS" DOUBLE,
"STRINGARGS" VARCHAR (100)
);

/* FILL THE TABLE */
/* NUMBER OF THREADS TO BE USED */
INSERT INTO APRIORI_PROCEDURE_CONFIGURATION VALUES ('THREAD_NUMBER', 2, null, null);
/* MINIMUM SUPPORT THRESHOLD */
INSERT INTO APRIORI_PROCEDURE_CONFIGURATION VALUES ('MIN_SUPPORT', null, 0.02, null);
/* MINIMUM CONFIDENCE THRESHOLD */
INSERT INTO APRIORI_PROCEDURE_CONFIGURATION VALUES ('MIN_CONFIDENCE', null, 0.02, null);

/* CALL THE APRIORI ALGORITHM */
CALL SYSTEM.PAL_APRIORI_RULE(TRANSACTIONS_VIEW, APRIORI_PROCEDURE_CONFIGURATION, APRIORI_RESULT, APRIORI_PMML_MODEL) WITH OVERVIEW;

/* SHOW THE RESULTS */
SELECT * FROM APRIORI_RESULT;
SELECT * FROM APRIORI_PMML_MODEL;
