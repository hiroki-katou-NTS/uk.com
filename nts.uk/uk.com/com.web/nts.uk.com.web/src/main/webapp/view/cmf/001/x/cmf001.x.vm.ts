/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
/// <reference path="../../../../lib/nittsu/nts.uk.com.web.nittsu.bundles.d.ts" />

module nts.uk.com.cmf001.x {


    @bean()
    export class ViewModel extends ko.ViewModel {

        // 受入設定の一覧
        settings: KnockoutObservableArray<any> = ko.observableArray([]);

        // 選択中の受入設定コード
        selectedSettingCode: KnockoutObservable<string> = ko.observable();

        // CSVファイル名
        csvFileName: KnockoutObservable<string> = ko.observable();

        // エラー出力
        errorsText: KnockoutObservable<string> = ko.observable();

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

        upload() {
            // サンプル実装
            $("#file-upload").ntsFileUpload({ stereoType: "csvfile" }).done(function(res) {
                console.log(res);
            }).fail(function(err) {
                console.log(err);
            });
        }

        csvFileUploaded() {

        }

    }

}