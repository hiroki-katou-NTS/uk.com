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
var servicePath = {
	getCategoryList: "/nts.uk.cnv.web/webapi/cnv/categorypriority/getcategories",
	getCategoryTables: "/nts.uk.cnv.web/webapi/cnv/conversiontable/getcategories",
	loadData: "/nts.uk.cnv.web/webapi/cnv/cnv001a/loaddata",
	addSource: "/nts.uk.cnv.web/webapi/cnv/conversiontable/source/add",
	delSource: "/nts.uk.cnv.web/webapi/cnv/conversiontable/source/delete",
	registRecord: "/nts.uk.cnv.web/webapi/cnv/conversiontable/record/regist",
	delRecord: "/nts.uk.cnv.web/webapi/cnv/conversiontable/record/delete",
	swap: "/nts.uk.cnv.web/webapi/cnv/conversiontable/record/swap"
}

var records;

var sources;

class record {
	constructor(recordNo, tableName, explanation, sourceId) {
		this.recordNo = recordNo;
		this.tableName = tableName;
		this.explanation = explanation;
		this.sourceId = sourceId;
	}
}

class source {
	constructor(sourceId, erpTableName, whereCondition, memo) {
		this.sourceId = sourceId;
		this.erpTableName = erpTableName;
		this.whereCondition = whereCondition;
		this.memo = memo;
	}
}

$(function(){
	startPage();

	function startPage() {
		$("#messageDialog").dialog({
			autoOpen: false,
			modal: true,
			buttons: {
				"OK": function(){ $(this).dialog('close'); }
			}
		});

		loadPage();
	}

	function loadPage() {
		clearPage();

		$.ajax(ajaxOption.build(servicePath.getCategoryList, {
		})).done(function (res) {
			var options = $.map(res, function (value, index) {
				return $('<option>', { value: index, text: value });
			});

			options.unshift($('<option>', { value: -1, text: "-- 未選択 --" }));

			$('#selCategory > option').remove();
			$("#selCategory").append(options);

		}).fail(function(rej){
			console.log(rej);
		});
	}

	function clearPage() {
		$('#selTable > option').remove();
		$("#selTable").prop('disabled', true);

		$("#selRecords > option").remove();
		$("#selSources > option").remove();
		$("#selErpTables > option").remove();
		$("#txtWhereCondition").val('');
		$("#txtMemo").val('');
		$("#txtRecordName").val('');
		recotds = [];
		sources = [];
	}

	function showMsg(msg) {
		$('#lblMessage').text(msg);
		$('#messageDialog').dialog('open');
	}

	function nextRecordNo() {
		if(typeof records === "undefined" || records.length === 0){
			return 1;
		}

		if(records.length === 1) {
			return Number(records[0].recordNo) + 1;
		}

		var max = records.reduce((a,b) => a.recordNo > b.recordNo ? a : b);

		return Number(max.recordNo) + 1;
	}

	$("#selCategory").change(function() {
		clearPage();

		var category = $("#selCategory option:selected").text();
		$("#selTable").prop('disabled', false);

		$.ajax(ajaxOption.build(servicePath.getCategoryTables,
			category
		)).done(function (res) {
			var options = $.map(res.tables, function (value, index) {
				return $('<option>', { value: index, text: value });
			});

			options.unshift($('<option>', { value: -1, text: "-- 未選択 --" }));

			$('#selTable > option').remove();
			$("#selTable").append(options);
		}).fail(function(rej){
			console.log(rej);
		});
	});

	$("#selTable").change(function() {
		var self = this;

		var category = $("#selCategory option:selected").text();
		var table = $("#selTable option:selected").text();

		$.ajax(ajaxOption.build(servicePath.loadData, {
			categoryName: category,
			tableName: table
		})).done(function (res) {

			records = $.map(res.records, function (value, index) {
				return new record(
						value.recordNo,
						value.tableName,
						value.explanation,
						value.sourceId);
			});
			sources = $.map(res.sources, function (value, index) {
				return new source(
						value.sourceId,
						value.erpTableName,
						value.whereCondition,
						value.memo);
			});

			var recordOptions = $.map(res.records, function (value, index) {
				return $('<option>', { value: value.recordNo, text: value.tableName + ' : ' + value.explanation });
			});
			var sourcesOptions = $.map(res.sources, function (value, index) {
				return $('<option>', { value: value.sourceId, text: value.erpTableName + ' : ' + value.memo });
			});
			var erpTableOptions = $.map(res.erpTables, function (value, index) {
				return $('<option>', { value: value, text: value });
			});

			$("#selRecords > option").remove();
			$("#selRecords").append(recordOptions);

			$("#selSources > option").remove();
			$("#selSources").append(sourcesOptions);

			$("#selErpTables > option").remove();
			$("#selErpTables").append(erpTableOptions);

		}).fail(function(rej){
			console.log(rej);
		});
	});


	$("#selRecords").change(function() {
		var recordNo = Number($("#selRecords").val());

		var selectedRecord = records.find(r => r.recordNo === recordNo);
		var selectedSource = sources.find(s => s.sourceId === selectedRecord.sourceId);

		$("#selSources").val(selectedRecord.sourceId);
		$("#txtWhereCondition").val(selectedSource.whereCondition);
		$("#txtMemo").val(selectedSource.memo);

		$("#txtRecordName").val(selectedRecord.explanation);

	});

	$("#btnAddSource").click(function() {
		var category = $("#selCategory option:selected").text();
		var erpTable = $("#selErpTables option:selected").text();
		var condition = $("#txtWhereCondition").val();
		var memo = $("#txtMemo").val();

		$.ajax(ajaxOption.build(servicePath.addSource, {
			sourceId: '',
			category: category,
			sourceTableName: erpTable,
			condition: condition,
			memo: memo
		})).done(function (res) {
			sources.push(new source(
					res.sourceId,
					erpTable,
					condition,
					memo));
			$("#selSources").append($('<option>', { value: res.sourceId, text: erpTable + ' : ' + memo }));
		});
	});

	$("#btnDelSource").click(function() {
		var sourceId = $("#selSources").val();

		$.ajax(ajaxOption.build(servicePath.delSource,
			sourceId
		)).done(function (res) {
			$("#selSources option:selected").remove();
		});
	});

	$("#selSources").change(function() {
		var sourceId = $("#selSources").val();
		var selectedSource = sources.find(s => s.sourceId === sourceId);

		$("#txtWhereCondition").val(selectedSource.whereCondition);
		$("#txtMemo").val(selectedSource.memo);
		$("#selErpTables").val(selectedSource.erpTableName);

	});

	$("#btnClear").click(function() {
		$("#selSources").val('');
		$("#txtWhereCondition").val('');
		$("#txtMemo").val('');

	});

	$("#btnRegistRecord").click(function() {
		var category = $("#selCategory option:selected").text();
		var table = $("#selTable option:selected").text();

		var recordNo = ($("#selRecords").val() === null)
			? nextRecordNo()
			: Number($("#selRecords").val());

		var sourceId = $("#selSources").val();
		var explanation = $("#txtRecordName").val();

		if( $("#selCategory option:selected").val() === -1 ||
			$("#selTable option:selected").val() === -1 ||
			sourceId === null) {

			showMsg("必須項目が未入力です");
			return;
		}

		$.ajax(ajaxOption.build(servicePath.registRecord, {
			category: category,
			table: table,
			recordNo: recordNo,
			sourceId: sourceId,
			explanation: explanation
		})).done(function (res) {

			if($("#selRecords").val() === null) {
				$("#selRecords").append($('<option>', { value: recordNo, text: table + ' : ' + explanation }));
			}
			else {
				var option = $("#selRecords option:selected");
				option.text(table + ' : ' + explanation);

				records = records.filter(r => r.recordNo !== recordNo);
			}

			records.push(new record(
				recordNo,
				table,
				explanation,
				sourceId));

			showMsg("登録しました");
		});
	});

	$("#btnNewRecord").click(function() {
		loadPage();
	});

	$("#btnDeleteRecord").click(function() {
		var category = $("#selCategory option:selected").text();
		var table = $("#selTable option:selected").text();
		var recordNo = Number($("#selRecords").val());

		if( $("#selCategory option:selected").val() === -1 ||
			$("#selTable option:selected").val() === -1 ||
			$("#selRecords").val() === null) {
			showMsg("レコードが未選択です");
			return;
		}

		$.ajax(ajaxOption.build(servicePath.delRecord, {
			category: category,
			table: table,
			recordNo: recordNo
		})).done(function (res) {
			var newRecord = records.filter(r => r.recordNo !== recordNo );
			records = newRecord;
			$("#selRecords option:selected").remove();

			showMsg("削除しました");
		});
	});

	$("#toUp").click(function() {
		if ($("#selRecords").val() === null) return;

		var recordNo = Number($("#selRecords").val());
		var prevRecordNo = recordNo - 1;

		var activeRecord = records.filter(r => r.recordNo == recordNo )[0];
		var prevRecord = records.filter(r => r.recordNo == prevRecordNo )[0];

		if(prevRecord === null) return;

		var category = $("#selCategory option:selected").text();
		var table = $("#selTable option:selected").text();
		$.ajax(ajaxOption.build(servicePath.swap, {
			category: category,
			table: table,
			recordNo1: recordNo,
			recordNo2: prevRecordNo,
		})).done(function (res) {
			activeRecord.recordNo = prevRecordNo;
			prevRecord.recordNo = recordNo;
			records.splice(prevRecordNo, 1, activeRecord);
			records.splice(recordNo, 1, prevRecord);

			var selected = $("#selRecords option:selected");

			selected.val(prevRecordNo);
			selected.prev().val(recordNo);

			selected.insertBefore(selected.prev());
		});
	});
	$("#toDown").click(function() {
		if ($("#selRecords").val() === null) return;

		var recordNo = Number($("#selRecords").val());
		var nextRecordNo = recordNo + 1;

		var activeRecord = records.filter(r => r.recordNo == recordNo )[0];
		var nextRecord = records.filter(r => r.recordNo == nextRecordNo )[0];

		if(nextRecord === null) return;

		var category = $("#selCategory option:selected").text();
		var table = $("#selTable option:selected").text();
		$.ajax(ajaxOption.build(servicePath.swap, {
			category: category,
			table: table,
			recordNo1: recordNo,
			recordNo2: nextRecordNo,
		})).done(function (res) {
			activeRecord.recordNo = nextRecordNo;
			nextRecord.recordNo = recordNo;
			records.splice(nextRecordNo, 1, activeRecord);
			records.splice(recordNo, 1, nextRecord);

			var selected = $("#selRecords option:selected");

			selected.val(nextRecordNo);
			selected.next().val(recordNo);

			selected.insertAfter(selected.next());
		});
	});

	$("#btnInputDetail").click(function() {
		var category = $("#selCategory option:selected").text();
		var table = $("#selTable option:selected").text();
		var recordNo = Number($("#selRecords").val());

		var selectedRecord = records.find(r => r.recordNo === recordNo);

		if(typeof selectedRecord === "undefined") {
			showMsg("レコードが未選択です");
			return;
		}

		var selectedSource = sources.find(s => s.sourceId === selectedRecord.sourceId);

		if( $("#selCategory option:selected").val() === -1 ||
			$("#selTable option:selected").val() === -1 ||
			$("#selRecords").val() === null) {
			showMsg("レコードが未選択です");
			return;
		}

		window.open("../c/index.html?"
					+ "category=" + category
					+ "&table=" + table
					+ "&recordNo=" + recordNo
					+ "&explanation=" + selectedRecord.explanation
					+ "&erpTableName=" + selectedSource.erpTableName,
				null,
				'width=1400,height=800,toolbar=no,menubar=no,scrollbars=yes');
	});

});