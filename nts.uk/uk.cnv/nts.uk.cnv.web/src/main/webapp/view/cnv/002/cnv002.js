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
	$("#import").click(function () {
		$('#result').val('');
		$.ajax(ajaxOption.build("/nts.uk.cnv.web/webapi/cnv/tabledesign/import", {
			createTableSql: $('#createtablesql').val(),
			createIndexSql: $('#createindexsql').val(),
			commentSql: $('#commentsql').val(),
			type: $('[name="ddl_type"]:checked').val()
		})).done(function (res) {
			console.log(res);
			if (typeof res === "undefined") {
				$('#result').val('処理が完了しました。');
			}
			else {
				$('#result').val('処理が完了しました。' + res.message);
			}
		}).fail(function(rej){
			console.log(rej);
			if (typeof rej === "undefined" || typeof rej.responseStatus === "undefined" || typeof rej.stackTrace === "undefined") {
				$('#result').val('処理は異常終了しました。');
			}
			else {
				$('#result').val('処理は異常終了しました。' + rej.responseStatus + rej.stackTrace);
			}
		});
	});
});
$(function () {
	$("#view").click(function () {
		$('#tabledesign').val('');
		$('#result').val('');
		$.ajax(ajaxOption.buildWithDataType("/nts.uk.cnv.web/webapi/cnv/tabledesign/exportddl", "text", {
			tableName: $('#tablename').val(),
			type: $('[name="tabledesign_type"]:checked').val()
		})).done(function (res) {
			console.log(res);
			$('#tabledesign').val(res);
			if (typeof res.message === "undefined") {
				$('#result').val('処理が完了しました。');
			}
			else {
				$('#result').val('処理が完了しました。' + res.message);
			}
		}).fail(function(rej){
			console.log(rej);
			if (typeof rej.responseStatus === "undefined" || typeof rej.stackTrace === "undefined") {
				$('#result').val('処理は異常終了しました。');
			}
			else {
				$('#result').val('処理は異常終了しました。' + rej.responseStatus + rej.stackTrace);
			}
		});
	});
});