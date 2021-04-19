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

	$("#view").click(function () {
		$('#tabledesign').val('');
		$.ajax(ajaxOption.build("/nts.uk.cnv.web/webapi/td/ddl/exportddl", {
			tableName: $('#tablename').val(),
			type: $('[name="tabledesign_type"]:checked').val(),
			withComment:
				$('#withComment').prop('checked') ? true : false
		})).done(function (res) {

			$('#tabledesign').val(res.ddl);
			if (typeof res.message === "undefined") {
				showMsg('処理が完了しました。');
			}
			else {
				showMsg('処理が完了しました。' + res.message);
			}
		}).fail(function(rej){
			console.log(rej);
			if (typeof rej.responseStatus === "undefined" || typeof rej.stackTrace === "undefined") {
				showMsg('処理は異常終了しました。');
			}
			else {
				showMsg('処理は異常終了しました。' + rej.responseStatus + rej.stackTrace);
			}
		});
	});
});