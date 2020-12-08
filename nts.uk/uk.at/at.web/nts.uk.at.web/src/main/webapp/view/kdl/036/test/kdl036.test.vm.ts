module nts.uk.at.view.kaf022.x {
    export module viewmodel {
        import modal = nts.uk.ui.windows.sub.modal;
        import getShared = nts.uk.ui.windows.getShared;
        import setShared = nts.uk.ui.windows.setShared;
        export class ScreenModel {
            employeeId: KnockoutObservable<string>;
            dateValue: KnockoutObservable<any>;
            daysUnit: KnockoutObservable<number>;
            targetSelectionAtr: KnockoutObservable<number>;
            selected: KnockoutObservable<string>;

            constructor() {
                const self = this;
                self.employeeId = ko.observable(__viewContext.user.employeeId);
                self.dateValue = ko.observable({startDate: null, endDate: null});
                self.daysUnit = ko.observable(0.5);
                self.targetSelectionAtr = ko.observable(0);
                self.selected = ko.observable(null);
            }

            openKdl035() {
                $(".nts-input").trigger("validate");
                if (nts.uk.ui.errors.hasError()) {
                    return;
                }
                const self = this;
                const params: any = {
                    // 社員ID
                    employeeId: self.employeeId(),

                    // 申請期間
                    period: self.dateValue(),

                    // 日数単位（1.0 / 0.5）
                    daysUnit: self.daysUnit(),

                    // 対象選択区分（自動 / 申請 / 手動
                    targetSelectionAtr: self.targetSelectionAtr(),

                    // List<表示する実績内容>
                    actualContentDisplayList: [],

                    // List<振出振休紐付け管理>
                    managementData: JSON.parse(self.selected()) || []
                };
                setShared("KDL036_PARAMS", params);
                modal("/view/kdl/036/a/index.xhtml").onClosed(() => {
                    const data = getShared("KDL036_RESULT");
                    if (data)
                        self.selected(JSON.stringify(data));
                });
            }
        }
    }
}