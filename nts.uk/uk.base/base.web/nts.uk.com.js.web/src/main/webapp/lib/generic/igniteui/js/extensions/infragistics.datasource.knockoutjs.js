/*!@license
* Infragistics.Web.ClientUI igDataSource KnockoutJS extension 19.1.20
*
* Copyright (c) 2011-2019 Infragistics Inc.
*
* http://www.infragistics.com/
*
* Depends on:
*	jquery-1.9.1.js
*	ig.util.js
*	ig.dataSource.js
*/
(function(factory){if(typeof define==="function"&&define.amd){define(["../modules/infragistics.datasource","knockout"],factory)}else{factory(jQuery,ko)}})(function($,ko){$.ig.KnockoutDataSource=$.ig.KnockoutDataSource||$.ig.DataSource.extend({init:function(options){if(options.observableDataSource===null||options.observableDataSource===undefined){this.kods=options.dataSource;options.dataSource=ko.toJS(options.dataSource)}else{this.kods=options.observableDataSource;options.dataSource=ko.toJS(options.observableDataSource)}this._super(options);return this},setCellValue:function(rowId,colId,val,autoCommit){return this._super(rowId,colId,val,autoCommit)},_setCellValue:function(rowId,colId,val){var cellData,pk=this.settings.primaryKey,ds=this.kods,i;ds=ko.isObservable(ds)?ds():ds;if(this.settings.responseDataKey){if(ko.isObservable(ds[this.settings.responseDataKey])){ds=ds[this.settings.responseDataKey]()}else if(ds[this.settings.responseDataKey]){ds=ds[this.settings.responseDataKey]}}if(pk){for(i=0;i<ds.length;i++){cellData=ko.isObservable(ds[i][pk])?ds[i][pk]():ds[i][pk];if(String(cellData)===String(rowId)){if(ko.isWriteableObservable(ds[i][colId])){ds[i][colId](val)}else{ds[i][colId]=val}break}}}else{if(ko.isWriteableObservable(ds[rowId][colId])){ds[rowId][colId](val)}else{ds[rowId][colId]=val}}},deleteRow:function(rowId,autoCommit){if(autoCommit){this._ownUpdate=!this._ownUpdate}return this._super(rowId,autoCommit)},_deleteRow:function(rowId){var pk=this.settings.primaryKey,ds=this.kods;if(this.settings.responseDataKey&&ds[this.settings.responseDataKey]){ds=ds[this.settings.responseDataKey]}if(ko.isObservable(ds)){if(!this._koUpdate){ds.remove(function(item){return String(ko.isObservable(item[pk])?item[pk]():item[pk])===String(rowId)})}else{this._koUpdate=false}}else{this._super(rowId)}},addRow:function(rowId,rowObject,autoCommit){if(autoCommit){this._ownUpdate=!this._ownUpdate}return this._super(rowId,rowObject,autoCommit)},_addRow:function(rowObject,rowId,origDs){var ds=this.kods,prop,koObject=jQuery.extend({},rowObject);if(this.settings.responseDataKey&&ds[this.settings.responseDataKey]){ds=ds[this.settings.responseDataKey]}if(ko.isObservable(ds)){if(!this._koUpdate){for(prop in koObject){if(koObject.hasOwnProperty(prop)&&!ko.isObservable(prop)){koObject[prop]=ko.observable(koObject[prop])}}this._super(rowObject,rowId,origDs);ds.push(koObject)}else{this._koUpdate=false;this._super(rowObject,rowId,origDs)}}else{ds.push(rowObject);this._super(rowObject,rowId,origDs)}},updateRow:function(rowId,rowObject,autoCommit){if(autoCommit){this._ownUpdate=!this._ownUpdate}return this._super(rowId,rowObject,autoCommit)},_updateRow:function(rowId,rowObject){var cellData,pk=this.settings.primaryKey,ds=this.kods,prop,i;if(this.settings.responseDataKey&&ds[this.settings.responseDataKey]){ds=ds[this.settings.responseDataKey]}ds=ko.isObservable(ds)?ds():ds;if(pk){for(i=0;i<ds.length;i++){cellData=ko.isObservable(ds[i][pk])?ds[i][pk]():ds[i][pk];if(String(cellData)===String(rowId)){for(prop in rowObject){if(rowObject.hasOwnProperty(prop)){if(ko.isWriteableObservable(ds[i][prop])){ds[i][prop](rowObject[prop])}else{ds[i][prop]=rowObject[prop]}}}break}}}else{for(prop in rowObject){if(rowObject.hasOwnProperty(prop)){if(ko.isWriteableObservable(ds[rowId][prop])){ds[rowId][prop](rowObject[prop])}else{ds[rowId][prop]=rowObject[prop]}}}}},_commitTransaction:function(t){var i,prop,rec,origRec,origDs=this.settings.localSchemaTransform?this._origDs:null;if(origDs===this._data){origDs=null}this._ownUpdate=true;if(this.settings.primaryKey===null){rec=this._data[parseInt(t.rowId,10)];if(origDs){origRec=origDs[parseInt(t.rowId,10)]}}else{rec=this.findRecordByKey(t.rowId);if(origDs){origRec=this.findRecordByKey(t.rowId,origDs)}}if(t.type==="cell"){rec[t.col]=t.value;if(origRec){origRec[t.col]=t.value}this._setCellValue(t.rowId,t.col,t.value)}else if(t.type==="row"){if($.type(t.row)==="array"){for(i=0;i<t.row.length;i++){rec[i]=t.row[i];if(origRec){origRec[i]=t.row[i]}}}else if(rec){for(prop in t.row){if(t.row.hasOwnProperty(prop)){rec[prop]=t.row[prop];if(origRec){origRec[prop]=t.row[prop]}}}this._updateRow(t.rowId,t.row)}}else if(t.type==="deleterow"){if(this.settings.primaryKey===null){this.removeRecordByIndex(parseInt(t.rowId,10),origDs)}else{this.removeRecordByKey(t.rowId,origDs)}this._deleteRow(t.rowId)}else if(t.type==="newrow"){this._addRow(t.row,t.rowId,origDs)}else if(t.type==="insertrow"){this._addRow(t.row,t.rowIndex,origDs)}this._removeTransactionByTransactionId(t.tid)},dataAt:function(path,keyspath){var data=this.kods,paths=path.split("/"),kp=keyspath.split("/"),k,i,tmp,j,cd=null,ckey=this.settings.primaryKey,ckeyval="",ckeys=[],ckeyvals=[],match=false;data=ko.isObservable(data)?data():data;for(i=0;i<paths.length;i++){ckey=paths[i].split(":")[0];ckeyval=paths[i].split(":")[1];if(paths[i]!==""){for(j=0;data&&j<data.length;j++){if(ckey&&ckey.indexOf(",")!==-1){ckeys=ckey.split(",");ckeyvals=ckeyval.split(",");for(k=0;k<ckeys.length;k++){tmp=data[j][ckeys[k]];tmp=ko.isObservable(tmp)?tmp():tmp;if(!tmp.charAt&&ckeyvals[k].charAt){ckeyvals[k]=parseInt(ckeyvals[k],10)}match=tmp===ckeyvals[k];if(!match){break}}}else{tmp=data[j][ckey];tmp=ko.isObservable(tmp)?tmp():tmp;if(tmp!==undefined&&!tmp.charAt&&ckeyval.charAt){ckeyval=parseInt(ckeyval,10)}match=tmp===ckeyval}if(match){cd=data[j][kp[i]];if(paths.length>1&&i<paths.length-1&&this.settings.responseDataKey&&$.type(cd)!=="array"&&cd[this.settings.responseDataKey]){if(ko.isObservable(cd[this.settings.responseDataKey])){cd=cd[this.settings.responseDataKey]()}else{cd=cd[this.settings.responseDataKey]}}break}}data=ko.isObservable(cd)?cd():cd}}return cd}});return $.ig.KnockoutDataSource});