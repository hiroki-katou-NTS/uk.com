var server = "http://" + location.host;
var ajaxOption = {
	type: "POST",
	contentType: "application/json",
	dataType: "json",

	build: function (path, data) {
		this.dataType = "json";
		return $.extend({url:server + path, data: JSON.stringify(data)}, this);
	},
	buildWithDataType: function (path, dataType, data) {
		this.dataType = dataType;
		return $.extend({url:server + path, data: JSON.stringify(data)}, this);
	}
};
var ajaxOptionGet = {
		type: "GET",
		contentType: "application/json",
		dataType: "json",

		build: function (path, data) {
			this.dataType = "json";
			return $.extend({url:server + path, data: JSON.stringify(data)}, this);
		}
	};
var servicePath = {
    getukcolumns_oruta: "/nemunoki.oruta.web/webapi/tables/name",
    getukcolumns_cnv: "/nts.uk.cnv.web/webapi/cnv/tableinfo/getukcolumns",
    geterpcolumns: "/nts.uk.cnv.web/webapi/cnv/tableinfo/geterpcolumns",
    find: "/nts.uk.cnv.web/webapi/cnv/conversiontable/find",
    regist: "/nts.uk.cnv.web/webapi/cnv/conversiontable/regist",
    test: "/nts.uk.cnv.web/webapi/cnv/conversiontable/test",
    test_update: "/nts.uk.cnv.web/webapi/cnv/conversiontable/test_update"
}

var category;
var ukTable;
var tableId;
var erpTable;
var recordNo;

var ukColumnsList;
var erpColumnsList;

var registeredColumnList;

var TabMap = {
	"NULL": "#null",
	"NONE": "#none",
	"CODE_TO_ID": "#codeToId",
	"CODE_TO_CODE": "#codeToCode",
	"FIXID_VALUE": "#fixedValue",
	"FIXID_VALUE_WITH_CONDITION": "#fixedCalueWithCond",
	"PARENT": "#parent",
	"STRING_CONCAT": "#stringConcat",
	"TIME_WITH_DAY_ATTR": "#timeWithDayAttr",
	"DATETIME_MERGE": "#datetimeMerge",
	"GUID": "#guid",
	"PASSWORD": "#password",
	"FILE_ID": "#fileId",
	"SOURCE_JOIN": "#sourceJoin"
};

class columnData {
	constructor(name, dataType, length, scale, nullable) {
		this.name = name;
		this.dataType = dataType;
		this.length = length;
		this.scale = scale;
		this.nullable = nullable;
	}
}

$(function(){
	startPage();

	function startPage() {
		var arg = new Object;
		var pair = decodeURI(location.search).substring(1).split('&');
		for(var i=0;pair[i];i++) {
		    var kv = pair[i].split('=');
		    arg[kv[0]]=kv[1];
		}

		category = arg["category"];
		ukTable = arg["table"];
		tableId = arg["tableId"];
		recordNo = arg["recordNo"];
		erpTable = arg["erpTableName"];

		$("#lblCategory").text(category);
		$("#lblUkTableName").text(ukTable);
		$("#lblExplanation").text(arg["explanation"]);
		$("#lblErpTableName").text(erpTable);

		$('#tabs').tabs();
		$("#messageDialog").dialog({
			autoOpen: false,
			modal: true,
			buttons: {
				"OK": function(){ $(this).dialog('close'); }
			}
		});
		$("#msgTestDialog").dialog({
			autoOpen: false,
			modal: false,
			width: 450,
			height:400,
			buttons: {
				"OK": function(){ $(this).dialog('close'); }
			}
		});

		$.ajax(
			$.extend({url:server + servicePath.getukcolumns_oruta + '/' + ukTable + '/not-accepted'}, {type: "GET"})
		).done(function (res) {
			ukColumnsList = $.map(res.columns, function (value, index) {
					return new columnData(value.name, value.type.dataType, value.type.length, value.type.scale, value.type.nullable);
				});

			$.ajax(ajaxOption.build(servicePath.getukcolumns_cnv,{
				category: category,
				tableName: ukTable,
				recordNo: recordNo
			})).done(function (res) {
				registeredColumnList = res;

				viewUkColumnList();
			}).fail(function(rej){
				console.log(rej);
			});

			$.ajax(ajaxOption.build(servicePath.geterpcolumns,{
				tableName: erpTable
			})).done(function (res) {
				var options = $.map(res, function (value, index) {
					var length = 25 - getLen(value.columnName);
					if (length <= 0) {
						length = 1;
					}
					return $('<option>', { value: value.columnName, text: value.columnName + String.fromCharCode( 160 ).repeat(length) + " : " + value.dataType });
				});

				erpColumnsList = $.map(res, function (value, index) {
						return new columnData(value.name, value.type);
					});

				$(".selSourceColumn > option").remove();
				$(".selSourceColumn").append(options);

				$(".selNullable").prepend($('<option>', { value: "", text:"-- ????????? --" }));
				$(".selNullable").val("");

			}).fail(function(rej){
				console.log(rej);
			});
		}).fail(function(rej){
			console.log(rej);
		});

		loadPage();
	}

	function viewUkColumnList() {
		options = $.map(ukColumnsList, function(value, index) {
			var registered = ($.inArray(value.name, registeredColumnList) !== -1);
			var mark = registered ? "???" : "???";
			var length = 25 - getLen(value.name);
			if (length <= 0) {
				length = 1;
			}
			return $('<option>', {
					value: value.name,
					text: mark + " " + value.name + String.fromCharCode(160).repeat(length) + " : " + value.dataType + "(" + value.length + "," + value.scale + ")" });
		});

		$('#selUkColumns > option').remove();
		$("#selUkColumns").append(options);
	}

	$("#selUkColumns").change(function() {
		selectColumn();
	});

	function selectColumn() {
		var ukColumnName = $("#selUkColumns").val();

		$.ajax(ajaxOption.build(servicePath.find, {
			category: category,
			table: ukTable,
			recordNo: recordNo,
			ukColumn: ukColumnName
		})).done(function (res) {
			loadPageFromTableConversionData(res);
		}).fail(function(rej){
			console.log(rej);
		});
	}

	$("#btnRegist").click(function() {

		var data = getPageData();

		$.ajax(ajaxOption.build(servicePath.regist,
			data
		)).done(function (res) {
			showMsg("??????????????????");

			registeredColumnList.push(data.targetColumn);

			viewUkColumnList();
		}).fail(function(rej){
			console.log(rej);
		});
	});

	$("#btnClose").click(function() {
		window.close();
	});

	$("#btnTest").click(function() {

		var targetColumn = $("#selUkColumns option:selected").val();

		if(targetColumn == null || targetColumn === "" || typeof targetColumn === "undefined") {
			showMsg("?????????UK?????????????????????????????????");
			return;
		}

		$("#txtTestOutput").val("");
		$.ajax(ajaxOption.buildWithDataType(servicePath.test, "text", {
			category: category,
			table: ukTable,
			recordNo: recordNo,
			ukColumn: targetColumn
		})).done(function (res) {
			$("#txtTestOutput").val(res);
			$("#msgTestDialog").dialog('open');
		}).fail(function(rej){
			console.log(rej);
			$("#txtTestOutput").val("??????????????????????????????????????????????????????");
			$("#msgTestDialog").dialog('open');
		});

	});

	$("#btnTest_update").click(function() {

		var targetColumn = $("#selUkColumns option:selected").val();

		if(targetColumn == null || targetColumn === "" || typeof targetColumn === "undefined") {
			showMsg("?????????UK?????????????????????????????????");
			return;
		}

		$("#txtTestOutput").val("");
		$.ajax(ajaxOption.buildWithDataType(servicePath.test_update, "text", {
			category: category,
			table: ukTable,
			recordNo: recordNo,
			ukColumn: targetColumn
		})).done(function (res) {
			$("#txtTestOutput").val(res);
			$("#msgTestDialog").dialog('open');
		}).fail(function(rej){
			console.log(rej);
			$("#txtTestOutput").val("??????????????????????????????????????????????????????");
			$("#msgTestDialog").dialog('open');
		});
	});

	$("#selConvType").change(function() {
		changeConvType();
	});

	function changeConvType() {
		var selectedType = $("#selConvType").val();

		loadPage();
		if(selectedType === null) return;

		var tabId = TabMap[selectedType];
		$(".ui-tab a[href='" + tabId + "']").trigger("click");

		$('.ui-tabs-active').show();
	}

	$("#btnLoadParent").click(function () {

		$(".selSourceParentColumn > option").remove();
		$(".selSourceParentColumn").append($('<option>', { value: "", text:"-- ????????? --" }));

		var parentTable = $("#txtParentTblName").val();
		if(typeof parentTable === "undefined" ) return;

		loadTableDesignByOruta(parentTable, function (options) {
			$(".selSourceParentColumn > option").remove();
			$(".selSourceParentColumn").append(options);
		});
	});

	$("#btnLoadSourceTbl").click(function () {

		$(".selJoinSourceColumn > option").remove();
		$(".selJoinSourceColumn").append($('<option>', { value: "", text:"-- ????????? --" }));
		var sourceTable = $("#txtSourceTblName").val();
		loadTableDesignByErp(sourceTable, function (options) {
			$(".selJoinSourceColumn > option").remove();
			$(".selJoinSourceColumn").append(options);
		});
	});

	function loadTableDesignByOruta(targetTable, setDataFunc) {

		if(targetTable === null) {
			return;
		}

		$.ajax(
				$.extend({url:server + servicePath.getukcolumns_oruta + '/' + targetTable + '/not-accepted'}, {type: "GET"})
		).done(function (res) {
			var options = $.map(res.columns, function (value, index) {
				return $('<option>', {
					value: value.name,
					text: value.name
						+ String.fromCharCode(160).repeat(25-getLen(value.name))
						+ " : "
						+ value.type.dataType
						+ "("
						+ value.type.length
						+ ","
						+ value.type.scale
						+ ")"
					});
			});

			setDataFunc(options);
		}).fail(function(rej){
			console.log(rej);
		});
	}

	function loadTableDesignByErp(targetTable, setDataFunc) {

		if(targetTable === null) {
			return;
		}

		$.ajax(ajaxOption.build(servicePath.geterpcolumns, {
			tableName: targetTable
		})).done(function (res) {
			var options = $.map(res, function (value, index) {
				return $('<option>', {value: value.columnName, text: value.columnName + String.fromCharCode(160).repeat(25-getLen(value.columnName)) + " : " + value.dataType});
			});

			setDataFunc(options);
		}).fail(function(rej){
			console.log(rej);
		});
	}

	$(".chkJoin").change(function() {
		var targetId = '#' + $(this).data('target');

		$(targetId).prop('disabled', ($(this).prop('checked') == false));
	});

	function loadPage() {
		//??????????????????
		$('.ui-tabs-tab').hide();
		$('.ui-widget-header').hide();
		$('.ui-tabs-panel').hide();

		$('#tabs .ui-tabs-nav').removeClass('ui-corner-all');
	}

	function showMsg(msg) {
		$('#lblMessage').text(msg);
		$('#messageDialog').dialog('open');
	}

	function getLen(str){
		var result = 0;
		for(var i=0;i<str.length;i++){
			var chr = str.charCodeAt(i);
			if((chr >= 0x00 && chr < 0x81) ||
				(chr === 0xf8f0) ||
				(chr >= 0xff61 && chr < 0xffa0) ||
				(chr >= 0xf8f1 && chr < 0xf8f4)){
				result += 1;
			}else{
				result += 2;
			}
		}
		return result;
	};

	function getPageData() {

		var targetColumn = $("#selUkColumns option:selected").val();
		var conversionType = $("#selConvType option:selected").val();

		if(targetColumn === null || targetColumn === "") {
			showMsg("?????????UK?????????????????????????????????");
			return;
		}

		if(conversionType === null || conversionType === "NULL") {
			showMsg("??????????????????????????????????????????");
			return;
		}

		// NONE
		var sourceColumn_none;

		// CODE_TO_CODE
		var codeToCodeType;
		var sourceColumn_codeToCode;

		// CODE_TO_ID
		var codeToIdType;
		var sourceColumn_codeToId;
		var sourceColumn_codeToId_ccd;

		// FIXID_VALUE
		var fixedValue;
		var fixedValueIsParam;

		// FIXID_VALUE_WITH_CONDITION
		var sourceColumn_fixedCalueWithCond;
		var operator;
		var conditionValue;
		var fixedValueWithCond;
		var fixedValueWithCondIsParam;

		// PARENT
		var parentTable;
		var sourceColumn_parent;
		var joinPKs;

		// STRING_CONCAT
		var sourceColumn1;
		var sourceColumn2;
		var delimiter;

		// TIME_WITH_DAY_ATTR
		var sourceColumn_timeWithDayAttr_dayAttr;
		var sourceColumn_timeWithDayAttr_time;

		// DATETIME_MERGE
		var sourceColumn_yyyymmdd;
		var sourceColumn_yyyy;
		var sourceColumn_mm;
		var sourceColumn_yyyymm;
		var sourceColumn_mmdd;
		var sourceColumn_dd;
		var sourceColumn_hh;
		var sourceColumn_mi;
		var sourceColumn_hhmi;
		var sourceColumn_ss;
		var sourceColumn_minutes;
		var sourceColumn_yyyymmddhhmi;
		var sourceColumn_yyyymmddhhmiss;

		// PASSWORD
		var sourceColumn_password;

		// FILE_ID
		var sourceColumn_fileId;
		var fileType;
		var sourceColumn_kojinId;

		// SOURCE_JOIN
		var joinSourceTable;
		var sourceColumn_sourceJoin;
		var joinSourcePKs;

		switch(conversionType){
		case "NONE":
			sourceColumn_none = $("#selSourceColumn_none option:selected").val();
			if(sourceColumn_none === "") {
				return;
			}
			break;
		case "CODE_TO_CODE":
			codeToCodeType = $("#txtCodeToCodeType").val();
			sourceColumn_codeToCode = $("#selSourceColumn_codeToCode option:selected").val();
			if(codeToCodeType === "" || sourceColumn_codeToCode === "") {
				showMsg("??????????????????????????????????????????");
				return;
			}
			break;
		case "CODE_TO_ID":
			codeToIdType = $("#selCodeToIdType option:selected").val();
			sourceColumn_codeToId = $("#selSourceColumn_codeToId option:selected").val();
			sourceColumn_codeToId_ccd = $("#selSourceColumn_codeToId_ccd option:selected").val();
			if(codeToIdType === "" || sourceColumn_codeToId === "") {
				showMsg("??????????????????????????????????????????");
				return;
			}
			break;
		case "FIXID_VALUE":
			fixedValue = $("#txtFixedValue").val();
			fixedValueIsParam = $("#chkFixedValueIsParam").prop("checked");
			if(fixedValue === "") {
				showMsg("??????????????????????????????????????????");
				return;
			}
			break;
		case "FIXID_VALUE_WITH_CONDITION":
			sourceColumn_fixedCalueWithCond = $("#selSourceColumn_fixedCalueWithCond option:selected").val();
			operator = $("#selOperator_fixedCalueWithCond option:selected").val();
			conditionValue = $("#txtConditionValue").val();
			fixedValueWithCond = $("#txtFixedValueWithCond").val();
			fixedValueWithCondIsParam = $("#chkFixedValueWithCondIsParam").prop("checked");
			if(sourceColumn_fixedCalueWithCond === ""
				|| operator === "NULL"
				|| conditionValue === ""
				|| fixedValueWithCond === "") {
				showMsg("??????????????????????????????????????????");
				return;
			}
			break;
		case "PARENT":
			parentTable = $("#txtParentTblName").val();
			sourceColumn_parent = $("#selSourceColumn_parent option:selected").val();

			var elems = $(".selJoinPK:not(:disabled)").sort(function(a,b){
				    if($(a).attr("id") < $(b).attr("id")) return -1;
				    if($(a).attr("id") > $(b).attr("id")) return 1;
				    return 0;
				});

			joinPKs = $.map(elems, function (elem, index) {
				return $(elem).val();
			}).join(',');

			if(parentTable === "" || sourceColumn_parent === "" || joinPKs === "") {
				showMsg("??????????????????????????????????????????");
				return;
			}
			break;
		case "STRING_CONCAT":
			sourceColumn1 = $("#selSourceColumn_stringConcat1 option:selected").val();
			sourceColumn2 = $("#selSourceColumn_stringConcat2 option:selected").val();
			delimiter = $("#txtDelimiter").val();
			if(sourceColumn1 === "" || sourceColumn1 === "") {
				showMsg("??????????????????????????????????????????");
				return;
			}
			break;
		case "TIME_WITH_DAY_ATTR":
			sourceColumn_timeWithDayAttr_dayAttr = $("#selSourceColumn_timeWithDayAttr_dayAttr option:selected").val();
			sourceColumn_timeWithDayAttr_time = $("#selSourceColumn_timeWithDayAttr_time option:selected").val();
			if(sourceColumn_timeWithDayAttr_dayAttr === "" || sourceColumn_timeWithDayAttr_time === "") {
				showMsg("??????????????????????????????????????????");
				return;
			}
			break;
		case "DATETIME_MERGE":
			sourceColumn_yyyymmdd = $("#selSourceColumn_yyyymmdd option:selected").val();
			sourceColumn_yyyy = $("#selSourceColumn_yyyy option:selected").val();
			sourceColumn_mm = $("#selSourceColumn_mm option:selected").val();
			sourceColumn_yyyymm = $("#selSourceColumn_yyyymm option:selected").val();
			sourceColumn_mmdd = $("#selSourceColumn_mmdd option:selected").val();
			sourceColumn_dd = $("#selSourceColumn_dd option:selected").val();
			sourceColumn_hh = $("#selSourceColumn_hh option:selected").val();
			sourceColumn_mi = $("#selSourceColumn_mi option:selected").val();
			sourceColumn_hhmi = $("#selSourceColumn_hhmi option:selected").val();
			sourceColumn_ss = $("#selSourceColumn_ss option:selected").val();
			sourceColumn_minutes = $("#selSourceColumn_minutes option:selected").val();
			sourceColumn_yyyymmddhhmi = $("#selSourceColumn_yyyymmddhhmi option:selected").val();
			sourceColumn_yyyymmddhhmiss = $("#selSourceColumn_yyyymmddhhmiss option:selected").val();
			break;
		case "GUID":
			break;
		case "PASSWORD":
			sourceColumn_password = $("#selSourceColumn_password option:selected").val();
			if(sourceColumn_password === "") {
				showMsg("??????????????????????????????????????????");
				return;
			}
			break;
		case "FILE_ID":
			sourceColumn_fileId = $("#selSourceColumn_fileId option:selected").val();
			fileType = $("#selFileType option:selected").val();
			sourceColumn_kojinId = $("#selSourceColumn_kojinId option:selected").val();
			if(sourceColumn_fileId === "" || fileType === "") {
				showMsg("??????????????????????????????????????????");
				return;
			}
			break;
		case "SOURCE_JOIN":
			joinSourceTable = $("#txtSourceTblName").val();
			sourceColumn_sourceJoin = $("#selSourceColumn_source option:selected").val();

			var elems = $(".selSourceJoinPK:not(:disabled)").sort(function(a,b){
				    if($(a).attr("id") < $(b).attr("id")) return -1;
				    if($(a).attr("id") > $(b).attr("id")) return 1;
				    return 0;
				});

			joinSourcePKs = $.map(elems, function (elem, index) {
				return $(elem).val();
			}).join(',');

			if(joinSourceTable === "" || sourceColumn_sourceJoin === "" || joinSourcePKs === "") {
				showMsg("??????????????????????????????????????????");
				return;
			}
			break;
		}

		return {
			category: category,
			table: ukTable,
			recordNo: recordNo,
			targetColumn: targetColumn,
			conversionType: conversionType,
			sourceTable: erpTable,
			sourceColumn_none: sourceColumn_none,
			codeToCodeType: codeToCodeType,
			sourceColumn_codeToCode: sourceColumn_codeToCode,
			codeToIdType: codeToIdType,
			sourceColumn_codeToId: sourceColumn_codeToId,
			sourceColumn_codeToId_ccd: sourceColumn_codeToId_ccd,
			fixedValue: fixedValue,
			fixedValueIsParam: fixedValueIsParam,
			sourceColumn_fixedCalueWithCond, sourceColumn_fixedCalueWithCond,
			operator: operator,
			conditionValue: conditionValue,
			fixedValueWithCond: fixedValueWithCond,
			fixedValueWithCondIsParam: fixedValueWithCondIsParam,
			parentTable: parentTable,
			sourceColumn_parent: sourceColumn_parent,
			joinPKs: joinPKs,
			sourceColumn1: sourceColumn1,
			sourceColumn2: sourceColumn2,
			delimiter: delimiter,
			sourceColumn_timeWithDayAttr_dayAttr: sourceColumn_timeWithDayAttr_dayAttr,
			sourceColumn_timeWithDayAttr_time: sourceColumn_timeWithDayAttr_time,
			sourceColumn_yyyymmdd: sourceColumn_yyyymmdd,
			sourceColumn_yyyy: sourceColumn_yyyy,
			sourceColumn_mm: sourceColumn_mm,
			sourceColumn_yyyymm: sourceColumn_yyyymm,
			sourceColumn_mmdd: sourceColumn_mmdd,
			sourceColumn_dd: sourceColumn_dd,
			sourceColumn_hh: sourceColumn_hh,
			sourceColumn_mi: sourceColumn_mi,
			sourceColumn_hhmi: sourceColumn_hhmi,
			sourceColumn_ss: sourceColumn_ss,
			sourceColumn_minutes: sourceColumn_minutes,
			sourceColumn_yyyymmddhhmi: sourceColumn_yyyymmddhhmi,
			sourceColumn_yyyymmddhhmiss: sourceColumn_yyyymmddhhmiss,
			sourceColumn_password: sourceColumn_password,
			sourceColumn_fileId: sourceColumn_fileId,
			fileType: fileType,
			sourceColumn_kojinId: sourceColumn_kojinId,
			joinSourceTable: joinSourceTable,
			sourceColumn_sourceJoin: sourceColumn_sourceJoin,
			joinSourcePKs: joinSourcePKs
		};
	}

	function loadPageFromTableConversionData(data) {

		if(typeof data === "undefined") {
			$("#selConvType option").prop("selected", false);
			changeConvType();
			return;
		}

		$("#selConvType").val(data.conversionType);

		var tabId = TabMap[data.conversionType];
		$(".ui-tab a[href='" + tabId + "']").trigger("click");
		$('.ui-tabs-active').show();

		switch(data.conversionType){
		case "NONE":
			$("#selSourceColumn_none").val(data.sourceColumn_none);
			break;
		case "CODE_TO_CODE":
			$("#txtCodeToCodeType").val(data.codeToCodeType);
			$("#selSourceColumn_codeToCode").val(data.sourceColumn_codeToCode);
			break;
		case "CODE_TO_ID":
			$("#selCodeToIdType").val(data.codeToIdType);
			$("#selSourceColumn_codeToId").val(data.sourceColumn_codeToId);
			break;
		case "FIXID_VALUE":
			$("#txtFixedValue").val(data.fixedValue);
			$("#chkFixedValueIsParam").prop("checked", data.fixedValueIsParam);
			break;
		case "FIXID_VALUE_WITH_CONDITION":
			$("#selSourceColumn_fixedCalueWithCond").val(data.sourceColumn_fixedCalueWithCond);
			$("#selOperator_fixedCalueWithCond").val(data.operator);
			$("#txtConditionValue").val(data.conditionValue);
			$("#txtFixedValueWithCond").val(data.fixedValueWithCond);
			$("#chkFixedValueWithCondIsParam").prop("checked", data.fixedValueWithCondIsParam);
			break;
		case "PARENT":
			$("#txtParentTblName").val(data.parentTable);

			if(typeof data.parentTable !== "undefined" ) {
				loadTableDesignByOruta(data.parentTable, function(options) {
					$(".selJoinSourceColumn > option").remove();
					$(".selJoinSourceColumn").append(options);

					$("#selSourceColumn_parent").val(data.sourceColumn_parent);

					$("#parent input[type='checkbox']").prop("checked", false);
					$.each(data.joinPKs.split(","), function (index, value) {
						$("#chkParentJoin" + (index + 1)).prop("checked", true);
						$("#selJoinPK"+ (index + 1)).prop('disabled', false);
						$("#selJoinPK" + (index + 1)).val(value)
					});
				});
			}
			break;
		case "STRING_CONCAT":
			$("#selSourceColumn_stringConcat1").val(data.sourceColumn1);
			$("#selSourceColumn_stringConcat2").val(data.sourceColumn2);
			$("#txtDelimiter").val(data.delimiter);
			break;
		case "TIME_WITH_DAY_ATTR":
			$("#selSourceColumn_timeWithDayAttr_dayAttr").val(data.sourceColumn_timeWithDayAttr_dayAttr);
			$("#selSourceColumn_timeWithDayAttr_time").val(data.sourceColumn_timeWithDayAttr_time);
			break;
		case "DATETIME_MERGE":
			$("#selSourceColumn_yyyymmdd").val(data.sourceColumn_yyyymmdd);
			$("#selSourceColumn_yyyy").val(data.sourceColumn_yyyy);
			$("#selSourceColumn_mm").val(data.sourceColumn_mm);
			$("#selSourceColumn_yyyymm").val(data.sourceColumn_yyyymm);
			$("#selSourceColumn_mmdd").val(data.sourceColumn_mmdd);
			$("#selSourceColumn_dd").val(data.sourceColumn_dd);
			$("#selSourceColumn_hh").val(data.sourceColumn_hh);
			$("#selSourceColumn_mi").val(data.sourceColumn_mi);
			$("#selSourceColumn_hhmi").val(data.sourceColumn_hhmi);
			$("#selSourceColumn_ss").val(data.sourceColumn_ss);
			$("#selSourceColumn_minutes").val(data.sourceColumn_minutes);
			$("#selSourceColumn_yyyymmddhhmi").val(data.sourceColumn_yyyymmddhhmi);
			$("#selSourceColumn_yyyymmddhhmiss").val(data.sourceColumn_yyyymmddhhmiss);
			break;
		case "GUID":
			break;
		case "PASSWORD":
			$("#selSourceColumn_password").val(data.sourceColumn_password);
			break;
		case "FILE_ID":
			$("#selSourceColumn_fileId").val(data.sourceColumn_fileId);
			break;
		case "SOURCE_JOIN":
			$("#txtSourceTblName").val(data.sourceTable);

			$(".selJoinSourceColumn > option").remove();
			$(".selJoinSourceColumn").append($('<option>', { value: "", text:"-- ????????? --" }));

			loadTableDesignByErp(data.sourceTable, function(options) {
				$(".selJoinSourceColumn > option").remove();
				$(".selJoinSourceColumn").append(options);

				$("#selSourceColumn_source").val(data.sourceColumn_sourceJoin);

				$("#parent input[type='checkbox']").prop("checked", false);
				$.each(data.joinSourcePKs.split(","), function (index, value) {
					$("#chkSourceJoin" + (index + 1)).prop("checked", true);
					$("#selSourceJoinPK"+ (index + 1)).prop('disabled', false);
					$("#selSourceJoinPK" + (index + 1)).val(value)
				});
			});
			break;
		}
	}
});