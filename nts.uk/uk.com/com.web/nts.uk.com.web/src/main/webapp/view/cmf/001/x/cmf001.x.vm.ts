/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
/// <reference path="../../../../lib/nittsu/nts.uk.com.web.nittsu.bundles.d.ts" />

module nts.uk.com.cmf001.x {
	import ajax = nts.uk.request.ajax;
	import info = nts.uk.ui.dialog.info;

	@bean()
	export class ViewModel extends ko.ViewModel {

		// 受入設定の一覧
		settings: KnockoutObservableArray<any> = ko.observableArray([]);

		// 選択中の受入設定コード
		selectedSettingCode: KnockoutObservable<string> = ko.observable();

		// CSVファイル名
		csvFileName: KnockoutObservable<string> = ko.observable();

		// CSVファイルID
		csvFileId: KnockoutObservable<string> = ko.observable();

		// エラー出力
		errorsText: KnockoutObservable<string> = ko.observable();

		// エラー出力
		isPreparedSuccess: KnockoutObservable<boolean> = ko.observable();

		constructor() {
			super();
		}

		mounted() {
			this.loadSettings().done(() => {
				$("body").removeClass("ko-cloak");
			});
		}

		loadSettings() {
			let dfd = $.Deferred();

			let path = "/exio/input/setting/find-all";

			this.$ajax(path).done(res => {
				this.settings(res);
				dfd.resolve();
			});

			return dfd.promise();
		}

		csvFileSelected() {

		}

		csvFileUploaded() {

		}

		canPrepare =  ko.computed(() => this.csvFileName());

		prepare(){
			// ファイルのアップロード
			$("#file-upload").ntsFileUpload({ stereoType: "csvfile" }).done(function(res) {
				console.log("成功！");
				nts.uk.ui._viewModel.content.csvFileId(res[0].id)
				let prepareCommand = {
					settingCode: nts.uk.ui._viewModel.content.selectedSettingCode(), 
					uploadedCsvFileId: nts.uk.ui._viewModel.content.csvFileId()};
				ajax("/exio/input/prepare", prepareCommand).done((prepareResult) => {
					// サーバーへタスクの進捗問い合わせ
					nts.uk.deferred.repeat(conf => conf
						.task(() => {
							return (<any>nts).uk.request.asyncTask.getInfo(prepareResult.id);
						})
						// 完了するまで問い合わせ続ける
						.while (info => info.pending || info.running)
						.pause(1000)).done((info: any) => {
							if(info.status ==="COMPLETED"){
								nts.uk.ui._viewModel.content.isPreparedSuccess(true);
								// 正常に完了していればエラーチェック
								let requestCount: number = 0;
								nts.uk.ui._viewModel.content.errorsText("");
								nts.uk.deferred.repeat(conf => conf
								.task(() => {
									requestCount++;
									return ajax("/exio/input/errors/" + nts.uk.ui._viewModel.content.selectedSettingCode() + "/" + requestCount).done((result) => {
										// 取得したエラーを蓄積
										nts.uk.ui._viewModel.content.errorsText(nts.uk.ui._viewModel.content.errorsText() + result.text);
									});
								})
								// エラー件数が0件になるまで繰り返す
								.while (result => result.errosCount === 0));
							}else{
								nts.uk.ui._viewModel.content.isPreparedSuccess(false);
								if(info.error.businessException){
									// 業務エラーの場合は画面に表示
									nts.uk.ui._viewModel.content.errorsText(info.error.message);
									console.log(info.error.message);
									console.log(nts.uk.ui._viewModel.content.errorsText());
								}else{
									// システムエラーの場合はエラーダイアログを表示
									(<any>nts).uk.request.specials.errorPages.systemError()
								}
							}
						});
				}).fail(function(err) {
					// 受入準備に失敗
					nts.uk.ui._viewModel.content.errorsText(nts.uk.resource.getMessage(err.messageId, []))
				})
			}).fail(function(err) {
				// ファイルのアップロードに失敗
				nts.uk.ui._viewModel.content.csvFileId("");
				nts.uk.ui._viewModel.content.errorsText(nts.uk.resource.getMessage(err.messageId, []))
			});
		}
		
    canExecute = ko.computed(() => this.csvFileId() && this.isPreparedSuccess() && !this.errorsText());

		execute(){
			let executeCommand = {
				settingCode: nts.uk.ui._viewModel.content.selectedSettingCode()};
			ajax("/exio/input/prepare", executeCommand).done((executeResult) => {
				// サーバーへタスクの進捗問い合わせ
				nts.uk.deferred.repeat(conf => conf
					.task(() => {
						return (<any>nts).uk.request.asyncTask.getInfo(executeResult.id);
					})
					// 完了するまで問い合わせ続ける
					.while (info => info.pending || info.running)
					.pause(1000)).done((info: any) => {
						if(info.stetus ==="COMPLETED"){
							console.log("おわった");
						}
					});

			}).fail(function(err) {
				// 受入実行に失敗
				nts.uk.ui._viewModel.content.errorsText(nts.uk.resource.getMessage(err.messageId, []))
			})

		}
	}
}