(function(){
	"use strict";
	
	var BASE_API_PATH = '/api/';
	
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
			this._oSearchData = new sap.ui.model.json.JSONModel();
			oView.setModel(this._oSearchData, 'SearchData');
			
			this._oTopRulesBySupport = new sap.ui.model.json.JSONModel();
			oView.setModel(this._oTopRulesBySupport, 'TopRulesBySupport');
			
			this._oTopRulesByConfidence = new sap.ui.model.json.JSONModel();
			oView.setModel(this._oTopRulesByConfidence, 'TopRulesByConfidence');
			
			this._oTopRulesByLift = new sap.ui.model.json.JSONModel();
			oView.setModel(this._oTopRulesByLift, 'TopRulesByLift');
			
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
				this._oTopRulesBySupport.setData(aResults[0]);
				this._oTopRulesByConfidence.setData(aResults[1]);
				this._oTopRulesByLift.setData(aResults[2]);
				
				this.unlockUI();
			}.bind(this))
			.catch(this._defaultPromiseError.bind(this));
		},
		
		runDemo : function(oEvent){
			this.lockUI();
			jQuery.get(APRIORI_GATHER_TWEETS_ENDPOINT + encodeURIComponent(oEvent.getParameter('query')))
			.then(function(oResult){
				this._oSearchData.setData(oResult);
				return jQuery.get(APRIORI_RUN_PATH);
			}.bind(this))
			.then(function(){
				return this.refreshModels();
			}.bind(this));
		},
		
		executeApriori: function(){
			this.lockUI();
			jQuery.get(APRIORI_RUN_PATH)
			.then(function(oResult){
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