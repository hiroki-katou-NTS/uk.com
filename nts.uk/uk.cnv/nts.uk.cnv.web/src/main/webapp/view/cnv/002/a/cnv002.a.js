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
$(function () {
	startPage();

	function startPage() {
		// メッセージダイアログ初期化
		$("#messageDialog").dialog({
			autoOpen: false,
			modal: true,
			buttons: {
				"OK": function(){ $(this).dialog('close'); }
			}
		});
	}

	function showMsg(msg) {
		$('#lblMessage').text(msg);
		$('#messageDialog').dialog('open');
	}

//	$("#import").click(function () {
//		$('#result').val('');
//		$.ajax(ajaxOption.build("/nts.uk.cnv.web/webapi/cnv/tabledesign/import", {
//			createTableSql: $('#createtablesql').val(),
//			createIndexSql: $('#createindexsql').val(),
//			commentSql: $('#commentsql').val(),
//			type: $('[name="ddl_type"]:checked').val(),
//			branch: $('#branch').val(),
//			date: $('#verDate').val()
//		})).done(function (res) {
//			console.log(res);
//			if (typeof res === "undefined") {
//				showMsg('処理が完了しました。');
//			}
//			else {
//				showMsg('処理が完了しました。' + res.message);
//			}
//		}).fail(function(rej){
//			console.log(rej);
//			if (typeof rej === "undefined" || typeof rej.responseStatus === "undefined" || typeof rej.stackTrace === "undefined") {
//				showMsg('処理は異常終了しました。');
//			}
//			else {
//				showMsg('処理は異常終了しました。' + rej.responseStatus + rej.stackTrace);
//			}
//		});
//	});
});