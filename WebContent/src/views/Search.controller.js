(function(){
	"use strict";
	sap.ui.controller("sap.ui.scpdemoday.views.Search", {

		onInit: function(){
			var oView = this.getView();
			
			//Set Models
			this._oProjectsModel = new sap.ui.model.json.JSONModel();
			oView.setModel(this._oProjectsModel, 'Projects');
			
			//Load data
			this.getOwnerComponent().getRouter().getRoute('search').attachPatternMatched(this._handleRouteMatched,this);
		},
		
		_handleRouteMatched : function(){
			
		},

		
	});
}());