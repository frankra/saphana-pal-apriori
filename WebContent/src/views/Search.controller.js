(function(){
	"use strict";
	
	var BASE_API_PATH = '/api/';
	
	var TEXT_JSON_PATH = "/src/assets/resources/text.json";
	
	var TWEETS_ENDPOINT = BASE_API_PATH + "Tweets/";
	
	var APRIORI_ENDPOINT = BASE_API_PATH + "Apriori/";
	var APRIORI_RUN_PATH = APRIORI_ENDPOINT + "run";
	var APRIORI_GATHER_TWEETS_ENDPOINT = BASE_API_PATH + "Apriori/gatherTweets?q=";
	var APRIORI_TOP_10_SUPPORT_ENDPOINT = BASE_API_PATH + "Apriori/getRulesBySupport?count=10";
	var APRIORI_TOP_10_CONFIDENCE_ENDPOINT = BASE_API_PATH + "Apriori/getRulesByConfidence?count=10";
	var APRIORI_TOP_10_LIFT_ENDPOINT = BASE_API_PATH + "Apriori/getRulesByLift?count=10";
	
	
	sap.ui.controller("sap.ui.scpdemoday.views.Search", {

		onInit: function(){
			var oView = this.getView();
			
			this.oPage = oView.byId("page");
			
			// Set Models
			this._oSearchDataModel = new sap.ui.model.json.JSONModel();
			oView.setModel(this._oSearchDataModel, 'SearchData');
			
			this._oAprioriResultModel = new sap.ui.model.json.JSONModel();
			oView.setModel(this._oAprioriResultModel, 'AprioriResult');
			
			this._oTextModel = new sap.ui.model.json.JSONModel();
			oView.setModel(this._oTextModel, 'Text');
			jQuery.get(TEXT_JSON_PATH)
			.then(function(oResponse){
				this._oTextModel.setData(oResponse);
			}.bind(this));
			
			this._oTopRulesBySupportModel = new sap.ui.model.json.JSONModel();
			oView.setModel(this._oTopRulesBySupportModel, 'TopRulesBySupport');
			
			this._oTopRulesByConfidenceModel = new sap.ui.model.json.JSONModel();
			oView.setModel(this._oTopRulesByConfidenceModel, 'TopRulesByConfidence');
			
			this._oTopRulesByLiftModel = new sap.ui.model.json.JSONModel();
			oView.setModel(this._oTopRulesByLiftModel, 'TopRulesByLift');
			
		},
		
		refreshModels : function(){
			this.lockUI();
			var oLoadSupportRules = jQuery.get(APRIORI_TOP_10_SUPPORT_ENDPOINT);
			var oLoadConfidenceRules = jQuery.get(APRIORI_TOP_10_CONFIDENCE_ENDPOINT);
			var oLoadLiftRules = jQuery.get(APRIORI_TOP_10_LIFT_ENDPOINT);
			
			Promise.all([
				oLoadSupportRules,
				oLoadConfidenceRules,
				oLoadLiftRules,
			])
			.then(function(aResults){
				this._oTopRulesBySupportModel.setData(aResults[0]);
				this._oTopRulesByConfidenceModel.setData(aResults[1]);
				this._oTopRulesByLiftModel.setData(aResults[2]);
				
				this.unlockUI();
			}.bind(this))
			.catch(this._defaultPromiseError.bind(this));
		},
		
		runDemo : function(oEvent){
			this.lockUI();
			jQuery.get(APRIORI_GATHER_TWEETS_ENDPOINT + encodeURIComponent(oEvent.getParameter('query')))
			.then(function(oResult){
				this._oSearchDataModel.setData(oResult);
				return jQuery.get(APRIORI_RUN_PATH);
			}.bind(this));
		},
		
		_formatSupport : function(dValue){
			return Math.round(dValue * 100);
		},
		
		executeApriori: function(){
			this.lockUI();
			jQuery.get(APRIORI_RUN_PATH)
			.then(function(oResult){
				this._oAprioriResultModel.setModel(oResult);
				this.refreshModels();
			}.bind(this));
		},
		
		_defaultPromiseError : function(e){
			alert("Something wrong happened");
			console.log(e);
			this.unlockUI();
		},
		
		lockUI : function(){
			this.oPage.setBusy(true);
		},
		
		unlockUI : function(){
			this.oPage.setBusy(false);
		}
		
	});
}());