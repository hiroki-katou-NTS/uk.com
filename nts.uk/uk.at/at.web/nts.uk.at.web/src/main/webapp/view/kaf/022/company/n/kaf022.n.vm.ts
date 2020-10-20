module nts.uk.at.view.kaf022.n.viewmodel {
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;

    export class ScreenModelN {
        columns = ko.observableArray([
            {headerText: getText("KAF022_681"), key: 'code', width: 30, columnCssClass: "grid-col-text-right", formatter: _.escape},
            {headerText: getText("KAF022_629"), key: 'name', width: 200, formatter: _.escape},
            {headerText: getText("KAF022_99"), key: 'useAtr', width: 70, formatter: makeIcon}
        ]);
        settings: KnockoutObservableArray<IOptionalItemAppSet>;
        selectedCode: KnockoutObservable<string>;

        constructor() {
            const self = this;
            self.settings = ko.observableArray([]);
            self.selectedCode = ko.observable(null);
        }

        initData(allData: any): void {
            const self = this;
            const listData = allData.optionalItemApplicationSettings || [];
            self.settings(_.sortBy(listData, [function(o) { return parseInt(o.code); }]));
        }

        openScreenP(): void {
            let self = this;
            modal('/view/kaf/022/p/index.xhtml').onClosed(() => {
                nts.uk.ui.block.invisible();
                nts.uk.request.ajax("at", "at/request/setting/company/applicationapproval/optionalitem/findall").done(data => {
                    self.settings(_.sortBy(data, [function(o) { return parseInt(o.code); }]));
                }).fail(error => {
                    nts.uk.ui.dialog.alertError(error);
                }).always(() => {
                    nts.uk.ui.block.clear();
                })
            });
        }

    }

    interface IOptionalItemAppSet {
        // コード
        code: string;
        // 申請種類名
        name: string
        // 利用区分
        useAtr: number;
    }

    function makeIcon(value) {
        if (value == "1") {
            return '<i class="icon icon-dot" style="margin: auto; display: block;"/>';
        }
        return '';
    }

}