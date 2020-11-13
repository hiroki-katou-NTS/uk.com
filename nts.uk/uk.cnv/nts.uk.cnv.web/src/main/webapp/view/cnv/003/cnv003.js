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
	testExport: "/nts.uk.cnv.web/webapi/cnv/codegenerator/excecute"
}

$(function(){
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

	$("#btnExport").click(function() {
		var filePath = $("#txtFilepath").val();

		var dbtype = $('[name="dbtype"]:checked').val()
		var sourceDbName = $("#txtSourceDbName").val();
		var sourceSchema = $("#txtSourceSchema").val();
		var targetDbName = $("#txtTargetDbName").val();
		var targetSchema = $("#txtTargetSchema").val();
		var contractCode = $("#txtContractCode").val();

		if (typeof filePath === "undefined" || filePath === "") {
			showMsg("ファイルパスを指定してください");
			return;
		}

		$.ajax(ajaxOption.build(servicePath.testExport, {
			dbtype: dbtype,
			sourceDbName: sourceDbName,
			sourceSchema: sourceSchema,
			targetDbName: targetDbName,
			targetSchema: targetSchema,
			contractCode: contractCode,
			filePath : filePath
		})).done(function (res) {
			showMsg("ファイル出力を完了しました");
		}).fail(function(rej){
			console.log(rej);
			showMsg("テスト出力でエラーが発生しました。" + rej);
			return;
		});
	});
});