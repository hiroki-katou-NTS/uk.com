module nts.uk.at.view.kdl029.a.screenModel {

    import dialog = nts.uk.ui.dialog.info;
    import text = nts.uk.resource.getText;
    import formatDate = nts.uk.time.formatDate;
    import block = nts.uk.ui.block;
    import jump = nts.uk.request.jump;
    import alError = nts.uk.ui.dialog.alertError;

    export class ViewModel {

        columns = ko.observableArray([
            { headerText: text('KAF011_14'), prop: 'code', width: 90 },
            { headerText: text('KAF011_15'), prop: 'text', width: 245 }
        ]);
        data = ko.observableArray([
            { code: 0, text: text('KAF011_14') },
            { code: 1, text: text('KAF011_15') }]);
        value: KnockoutObservable<string> = ko.observable('');

        ccgcomponent: any = {
            /** Common properties */
            systemType: 1, // システム区分
            showEmployeeSelection: true, // 検索タイプ
            showQuickSearchTab: false, // クイック検索
            showAdvancedSearchTab: true, // 詳細検索
            showBaseDate: false, // 基準日利用
            showClosure: false, // 就業締め日利用
            showAllClosure: false, // 全締め表示
            showPeriod: false, // 対象期間利用
            periodFormatYM: true, // 対象期間精度

            /** Required parame*/
            baseDate: moment.utc().toISOString(), // 基準日
            periodStartDate: moment.utc("1900/01/01", "YYYY/MM/DD").toISOString(), // 対象期間開始日
            periodEndDate: moment.utc("9999/12/31", "YYYY/MM/DD").toISOString(), // 対象期間終了日
            inService: true, // 在職区分
            leaveOfAbsence: true, // 休職区分
            closed: true, // 休業区分
            retirement: false, // 退職区分

            /** Quick search tab options */
            showAllReferableEmployee: true, // 参照可能な社員すべて
            showOnlyMe: true, // 自分だけ
            showSameWorkplace: true, // 同じ職場の社員
            showSameWorkplaceAndChild: true, // 同じ職場とその配下の社員

            /** Advanced search properties */
            showEmployment: true, // 雇用条件
            showWorkplace: true, // 職場条件
            showClassification: true, // 分類条件
            showJobTitle: true, // 職位条件
            showWorktype: false, // 勤種条件
            isMutipleCheck: false, // 選択モード

            /** Return data */
            returnDataFromCcg001: (data: any) => {
                let self: ViewModel = this;


            }
        };

        constructor() {
            let self = this;
            $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);

        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }
        start(): JQueryPromise<any> {            block.invisible();
            var self = this,
                dfd = $.Deferred();
            block.clear();

            dfd.resolve();

            return dfd.promise();

        }    }
}