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
	registCategoryPriority: "/nts.uk.cnv.web/webapi/cnv/categorypriority/regist",
	deleteCategoryPriority: "/nts.uk.cnv.web/webapi/cnv/categorypriority/delete",
    updateCategoryPriority: "/nts.uk.cnv.web/webapi/cnv/categorypriority/updatepriority",
	registCategory: "/nts.uk.cnv.web/webapi/cnv/conversiontable/category/regist",
	loaddata: "/nts.uk.cnv.web/webapi/cnv/cnv001b/loaddata",
	ukTableList: "/nemunoki.oruta.web/webapi/tables/list/not-accepted",
    regist: "/nts.uk.cnv.web/webapi/cnv/category/regist"
}
var ukTables = [];

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

		$.ajax(ajaxOption.build(servicePath.getCategoryList, {
		})).done(function (res) {
			var options = $.map(res, function (value, index) {
				return $('<option>', { value: index, text: value });
			});

			$('#category > option').remove();
			$("#category").append(options);

			selectCategory();

		}).fail(function(rej){
			console.log(rej);
		});

		$.ajax(ajaxOption.build(servicePath.getuktables, {
		})).done(function (res) {
			var options = $.map(res, function (value, index) {
				return $('<option>', { value: index, text: value });
			});

			$('#category > option').remove();
			$("#category").append(options);

			selectCategory();

		}).fail(function(rej){
			console.log(rej);
		});
	}

	function clearPage() {
		$("#category option").prop("selected", false);

		$("#categoryName").prop('disabled', false);
		$("#categoryName").val('');

		$('#uktableSelected > option').remove();
		$('#uktableUnSelected > option').remove();

		$("#tableListTitle").text("選択中一覧");

		selectCategory();
	}

	function showMsg(msg) {
		$('#lblMessage').text(msg);
		$('#messageDialog').dialog('open');
	}

	function selectCategory() {
		var category = $("#category option:selected").text();
		$("#categoryName").val(category);
		if(category != "") {
			$("#categoryName").prop('disabled', true);
		}

		var ukTableList;
		$.ajax(
			$.extend({url:server + servicePath.ukTableList}, {type: "GET"})
		).done(function (res) {
			ukTableList = $.map(res.tables, function (value, index) {
				return {tableId: value.tableId, name: value.name};
			});

			var optionsUnSelected;
			$.ajax(ajaxOption.build(servicePath.loaddata, {
				category: category
			})).done(function (res) {
				var optionsSelected = $.map(res.selectedTables, function (value, index) {
					return $('<option>', { value: value.tableId, text: value.name });
				});

				var optionsUnSelected = $.map(ukTableList, function (value, index) {
					var selected = res.selectedTables.some(t => t.tableId === value.tableId);
					return selected ? null : $('<option>', { value: value.tableId, text: value.name });
				});

				$('#uktableSelected > option').remove();
				$("#uktableSelected").append(optionsSelected);

				$('#uktableUnSelected > option').remove();
				$("#uktableUnSelected").append(optionsUnSelected);
			}).fail(function(rej){
				console.log(rej);
			});
		}).fail(function(rej){
			console.log(rej);
		});

	}

	$("#category").change(function() {
		selectCategory();
	});

	$("#btnRegist").click(function() {
		//カテゴリ優先順登録
		var categoryName = $("#categoryName").val();
		var categories = $('#category option').map(function(index, element){
			return element.text;
		}).get();

		if (categories.indexOf(categoryName) < 0 ){
			$("#category").append($('<option>', { value: categories.length, text: categoryName }));
			categories.push(categoryName);
		}

		var tables = $('#uktableSelected option').map(function(index, element){
			return {
				tableId: element.value,
				name: element.text
			};
		}).get();

		$.ajax(ajaxOption.build(servicePath.registCategoryPriority, {
			category : categoryName
		})).done(function (res) {
			$.ajax(ajaxOption.build(servicePath.updateCategoryPriority, {
				categories: categories
			})).done(function (res){
				
			}).fail(function(rej){
				console.log(rej);
				showMsg("登録でエラーが発生しました");
				return;
			});
		}).fail(function(rej){
			console.log(rej);
			showMsg("登録でエラーが発生しました");
			return;
		});

		//カテゴリ登録
		$.ajax(ajaxOption.build(servicePath.registCategory, {
			category: categoryName,
			tables: tables
		})).done(function (res) {
			$("#categoryListTitle").text("カテゴリ一覧");
			$("#tableListTitle").text("選択中一覧");
			showMsg("登録しました");
		}).fail(function(rej){
			console.log(rej);
			showMsg("登録でエラーが発生しました");
		});

		selectCategory();
	});

	$("#btnNew").click(function() {
		clearPage();
	});

	$("#btnDelete").click(function() {
		var select = $('#category > option:selected');
		if(typeof select === "undefined"){
			return;
		}
		select.remove();

		var categoryName = $("#categoryName").val();
		$.ajax(ajaxOption.build(servicePath.deleteCategoryPriority, {
			category: categoryName
		})).done(function (res) {

			$("#category").val();
			$("#categoryName").prop('disabled', false);
			$("#categoryName").val('');

			$('#uktableSelected > option').remove();
			$('#uktableUnSelected > option').remove();

			$("#tableListTitle").text("選択中一覧");
			showMsg("削除しました");

		}).fail(function(rej){
			console.log(rej);
			showMsg("削除でエラーが発生しました");
		});
	});

	$("#toLeft").click(function() {
		var option = $('#uktableUnSelected option:selected')[0];
		var selected = $('#uktableSelected > option:selected')[0];
		if (typeof selected === "undefined"){
			$('#uktableSelected').append(option);
		}
		else {
			selected.after(option);
		}
		$("#tableListTitle").text("選択中一覧　*");
	});
	$("#toRight").click(function() {
		var option = $('#uktableSelected option:selected')[0];
		var unSelected = $('#uktableUnSelected option:selected')[0];
		if (typeof unSelected === "undefined"){
			$('#uktableUnSelected').append(option);
		}
		else {
			unSelected.after(option);
		}
		$("#tableListTitle").text("選択中一覧　*");
	});

	$("#toUp").click(function() {
		var selected = $("#category option:selected");
		selected.insertBefore(selected.prev());

		$("#categoryListTitle").text("カテゴリ一覧　*");
	});
	$("#toDown").click(function() {
		var selected = $("#category option:selected");
		selected.insertAfter(selected.next());

		$("#categoryListTitle").text("カテゴリ一覧　*");
	});

	$("#table_toUp").click(function() {
		var selected = $("#uktableSelected option:selected");
		selected.insertBefore(selected.prev());

		$("#tableListTitle").text("選択中一覧　*");
	});
	$("#table_toDown").click(function() {
		var selected = $("#uktableSelected option:selected");
		selected.insertAfter(selected.next());

		$("#tableListTitle").text("選択中一覧　*");
	});
});