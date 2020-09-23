module nts.uk.at.view.kaf022.s.viewmodel {
    let __viewContext: any = window["__viewContext"] || {};
    import isNullOrEmpty = nts.uk.text.isNullOrEmpty;
    import dialogInfo = nts.uk.ui.dialog.info;
    import dialogConfirm =  nts.uk.ui.dialog.confirm;
    import alert = nts.uk.ui.dialog.alertError;
    import getText = nts.uk.resource.getText;

    export class ScreenModel {
        listReason: KnockoutObservableArray<IAppReasonStandard> = ko.observableArray([]);
        listReasonByAppType: KnockoutObservableArray<IAppReasonStandard> = ko.observableArray([]);
        selectedReason: KnockoutObservable<AppReasonStandard> = ko.observable(new AppReasonStandard(0));
        columns: KnockoutObservableArray<any>;
        listAppType: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        selectedAppType: KnockoutObservable<string> = ko.observable("0");
        selectedReasonCode: KnockoutObservable<number> = ko.observable(null);

        // bien theo doi update mode hay new mode
        isUpdate: KnockoutObservable<boolean> = ko.observable(false);

        constructor() {
            let self = this;
            self.columns = ko.observableArray([
                {headerText: getText("KAF022_694"), key: 'reasonCode', width: 100, formatter: _.escape},
                {headerText: getText("KAF022_443"), key: 'reasonTemp', width: 200, formatter: _.escape},
                {headerText: getText("KAF022_441"), key: 'defaultFlg', width: 50, formatter: makeIcon}

            ]);

            // list enum apptype get to combobox
            const labels = ["KAF022_3", "KAF022_4", "KAF022_5", "KAF022_6", "KAF022_7", "KAF022_8", "KAF022_11", "KAF022_707", "KAF022_10", "KAF022_12", "KAF022_705"];
            const listApplicationType = __viewContext.enums.ApplicationType;
            listApplicationType.forEach( (obj, index) => {
                if (obj.value == 1) {
                    let listHdType = __viewContext.enums.HolidayAppType;
                    listHdType.forEach(hdType => {
                        if (hdType.value < 7) {
                            self.listAppType.push(new ItemModel(obj.value, getText(labels[index]) + " - " + hdType.name, hdType.value))
                        }
                    })
                } else {
                    self.listAppType.push(new ItemModel(obj.value, getText(labels[index])));
                }
            });

            // subscribe a item in the list
            self.selectedReasonCode.subscribe((value) => {
                if (!isNullOrEmpty(value)) {
                    nts.uk.ui.errors.clearAll();
                    let reason: IAppReasonStandard = _.find(self.listReasonByAppType(), (o) => {
                        return o.reasonCode == value
                    });
                    if (!isNullOrEmpty(reason)) {
                        self.selectedReason(new AppReasonStandard(reason.appType, reason.hdAppType, reason));
                        self.isUpdate(true);
                        $("#reasonTemp").focus();
                    }
                } else {
                    const tmp = _.find(self.listAppType(), a => a.key == self.selectedAppType());
                    self.selectedReason(new AppReasonStandard(tmp.appType, tmp.hdAppType));
                    self.isUpdate(false);
                    nts.uk.ui.errors.clearAll();
                    $("#reasonCode").focus();
                }
            });

            // subscribe combobox for grid list change
            self.selectedAppType.subscribe((value) => {
                if (!_.isNil(value)) {
                    nts.uk.ui.errors.clearAll();
                    const tmp = _.find(self.listAppType(), a => a.key == value);
                    self.listReasonByAppType(_.sortBy(self.listReason().filter(r => r.appType == tmp.appType && r.hdAppType == tmp.hdAppType), ['reasonCode']));
                    if (self.listReasonByAppType().length > 0) {
                        if (self.selectedReasonCode() == self.listReasonByAppType()[0].reasonCode)
                            self.selectedReasonCode.valueHasMutated();
                        else
                            self.selectedReasonCode(self.listReasonByAppType()[0].reasonCode);
                    } else {
                        if (self.selectedReasonCode() != null)
                            self.selectedReasonCode(null);
                        else
                            self.selectedReasonCode.valueHasMutated();
                    }
                }
            });
        }

        /** get data to list **/
        getData(currentCode?: number): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            nts.uk.ui.block.invisible();
            service.getReason().done((lstData: Array<any>) => {
                if (lstData.length > 0) {
                    const tmp = [];
                    lstData.forEach(d => {
                        d.reasonTypeItemLst.forEach(i => {
                            tmp.push({
                                reasonCode: i.appStandardReasonCD,
                                dispOrder: i.displayOrder,
                                reasonTemp: i.reasonForFixedForm,
                                defaultFlg: i.defaultValue,
                                appType: d.applicationType,
                                hdAppType: d.opHolidayAppType
                            })
                        });
                    });
                    const listOrder = _.orderBy(tmp, ['dispOrder'], ['asc']);
                    self.listReason(listOrder);
                    if (isNullOrEmpty(currentCode)) {
                        self.selectedAppType.valueHasMutated();
                    } else {
                        const tmp = _.find(self.listAppType(), a => a.key == self.selectedAppType());
                        self.listReasonByAppType(self.listReason().filter(r => r.appType == tmp.appType && r.hdAppType == tmp.hdAppType));
                        if (self.selectedReasonCode() == currentCode)
                            self.selectedReasonCode.valueHasMutated();
                        else
                            self.selectedReasonCode(currentCode);
                    }
                } else {
                    self.listReason([]);
                    self.listReasonByAppType([]);
                    self.createNew();
                }
                dfd.resolve();
            }).fail(function (error) {
                dfd.reject();
                alert(error);
            }).always(() => {
                nts.uk.ui.block.clear();
            });
            return dfd.promise();
        }

        /** get data when start dialog **/
        startPage(): JQueryPromise<any> {
            const self = this;
            return self.getData();
        }

        // new button
        createNew() {
            let self = this;
            self.selectedReasonCode(null);
        }

        // close dialog
        closeDialog() {
            nts.uk.ui.windows.close();
        }

        /** update or insert data when click button register **/
        register() {
            const self = this;
            $('#reasonCode').trigger("validate");
            $('#reasonTemp').trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                const current = ko.toJS(self.selectedReason);
                if (!self.isUpdate() && _.find(self.listReasonByAppType(), i => i.reasonCode == current.reasonCode)) {
                    alert({ messageId: "Msg_3" });
                    return;
                }
                let data = ko.toJS(self.listReasonByAppType);
                if (!self.isUpdate()) {
                    data.push(current);
                } else {
                    data = data.map(d => {
                        if (d.reasonCode == current.reasonCode)
                            return current;
                        return d;
                    });
                }
                data.forEach((d, i) => {
                    d.dispOrder = i;
                });
                service.saveReason(data).done(() => {
                    dialogInfo({ messageId: "Msg_15" }).then(() => {
                        self.getData(current.reasonCode);
                    });
                }).fail(error => {
                    alert(error);
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            }
        }

        /** remove item from list **/
        remove() {
            const self = this;
            dialogConfirm({ messageId: "Msg_18" }).ifYes(() => {
                const current = self.selectedReasonCode();
                const currentIndex = _.findIndex(self.listReasonByAppType(), i => i.reasonCode == current);
                let nextIndex = currentIndex;
                if (currentIndex == self.listReasonByAppType().length - 1)
                    nextIndex = currentIndex - 1;
                service.deleteReason(ko.toJS(self.selectedReason)).done(() => {
                    self.listReasonByAppType.remove(i => i.reasonCode == current);
                    const data = ko.toJS(self.listReasonByAppType);
                    data.forEach((d, i) => {
                        d.dispOrder = i;
                    });
                    service.saveReason(data).done(() => {
                        const nextReasonCode = nextIndex > data.length || nextIndex < 0 ? null : data[nextIndex].reasonCode;
                        dialogInfo({ messageId: "Msg_16" }).then(() => {
                            self.getData(nextReasonCode)
                        });
                    }).fail(error => {
                        alert(error);
                    }).always(() => {
                        nts.uk.ui.block.clear();
                    });
                }).fail(error => {
                    alert(error);
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            }).ifNo(function () {
            });
        }

    }

    interface IAppReasonStandard {
        /** 理由ID */
        reasonCode: number;
        /** 表示順 */
        dispOrder: number;
        /** 定型理由*/
        reasonTemp: string;
        /** 既定*/
        defaultFlg: boolean;

        appType: number;

        hdAppType: number;
    }

    class AppReasonStandard {
        /** 理由ID */
        reasonCode: KnockoutObservable<number>;
        /** 表示順 */
        dispOrder: KnockoutObservable<number>;
        /** 定型理由*/
        reasonTemp: KnockoutObservable<string>;
        /** 既定*/
        defaultFlg: KnockoutObservable<boolean>;

        appType: number;

        holidayAppType: number;

        constructor(appType: number, hdAppType?: number, param?: IAppReasonStandard) {
            let self = this;
            self.appType = appType;
            self.reasonCode = ko.observable(param ? param.reasonCode : null);
            self.dispOrder = ko.observable(param ? param.dispOrder : 0);
            self.reasonTemp = ko.observable(param ? param.reasonTemp : "");
            self.defaultFlg = ko.observable(param ? param.defaultFlg : false);
            self.holidayAppType = hdAppType;
        }
    }

    class ItemModel {
        key: string;
        appType: number;
        hdAppType: number;
        name: string;

        constructor(appType: number, name: string, hdAppType?: number) {
            if (hdAppType)
                this.key = appType + "-" + hdAppType;
            else
                this.key = appType.toString();
            this.appType = appType;
            this.name = name;
            this.hdAppType = hdAppType;
        }
    }

    function makeIcon(value) {
        if (value == "true") {
            return '<i class="icon icon-dot" style="margin: auto; display: block;"/>';
        }
        return '';
    }
}