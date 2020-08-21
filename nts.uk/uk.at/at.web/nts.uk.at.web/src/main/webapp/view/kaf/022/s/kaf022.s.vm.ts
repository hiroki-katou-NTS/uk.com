module nts.uk.at.view.kaf022.s.viewmodel {
    let __viewContext: any = window["__viewContext"] || {};
    import isNullOrEmpty = nts.uk.text.isNullOrEmpty;
    import dialogInfo = nts.uk.ui.dialog.info;
    import dialogConfirm =  nts.uk.ui.dialog.confirm;
    import alert = nts.uk.ui.dialog.alert;
    import getText = nts.uk.resource.getText;

    export class ScreenModel {
        listReason: KnockoutObservableArray<IAppReasonStandard> = ko.observableArray([]);
        listReasonByAppType: KnockoutObservableArray<IAppReasonStandard> = ko.observableArray([]);
        selectedReason: KnockoutObservable<AppReasonStandard> = ko.observable(new AppReasonStandard(0));
        columns: KnockoutObservableArray<any>;
        listAppType: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        selectedAppType: KnockoutObservable<number> = ko.observable(0);
        selectedReasonCode: KnockoutObservable<number> = ko.observable(null);

        // bien theo doi update mode hay new mode
        isUpdate: KnockoutObservable<boolean> = ko.observable(true);

        constructor() {
            let self = this;
            self.columns = ko.observableArray([
                {headerText: getText("KAF022_694"), key: 'reasonCode', width: 100, formatter: _.escape},
                {headerText: getText("KAF022_443"), key: 'reasonTemp', width: 200, formatter: _.escape},
                {headerText: getText("KAF022_441"), key: 'defaultFlg', width: 50, formatter: makeIcon}

            ]);

            // list enum apptype get to combobox
            let listApplicationType = __viewContext.enums.ApplicationType;
            _.forEach(listApplicationType, (obj) => {
                self.listAppType.push(new ItemModel(obj.value, obj.name))
            });

            // subscribe a item in the list
            self.selectedReasonCode.subscribe((value) => {
                if (!isNullOrEmpty(value)) {
                    nts.uk.ui.errors.clearAll();
                    let reason: IAppReasonStandard = _.find(self.listReasonByAppType(), (o) => {
                        return o.reasonCode == value
                    });
                    if (!isNullOrEmpty(reason)) {
                        self.selectedReason(new AppReasonStandard(reason.appType, reason));
                        self.isUpdate(true);
                    }
                } else {
                    self.selectedReason(new AppReasonStandard(self.selectedAppType()));
                    self.isUpdate(false);
                    nts.uk.ui.errors.clearAll();
                }
            });

            // subscribe combobox for grid list change
            self.selectedAppType.subscribe((value) => {
                if (!_.isNil(value)) {
                    nts.uk.ui.errors.clearAll();
                    self.listReasonByAppType(self.listReason().filter(r => r.appType == value));
                    if (self.listReasonByAppType().length > 0) {
                        if (self.selectedReasonCode() == self.listReasonByAppType()[0].reasonCode)
                            self.selectedReasonCode.valueHasMutated();
                        else
                            self.selectedReasonCode(self.listReasonByAppType()[0].reasonCode);
                    } else {
                        self.selectedReasonCode(null);
                    }
                }
            });
        }

        /** get data to list **/
        getData(currentCode?: number): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            nts.uk.ui.block.grayout();
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
                                appType: d.applicationType
                            })
                        });
                    });
                    const listOrder = _.orderBy(tmp, ['dispOrder'], ['asc']);
                    self.listReason(listOrder);
                    if (isNullOrEmpty(currentCode)) {
                        self.selectedAppType.valueHasMutated();
                    } else {
                        self.listReasonByAppType(self.listReason().filter(r => r.appType == self.selectedAppType()));
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
            $('input').trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                const current = ko.toJS(self.selectedReason);
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
                    self.getData(current.reasonCode).done(() => {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
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
                        self.getData(nextReasonCode).done(() => {
                            nts.uk.ui.dialog.info({ messageId: "Msg_16" });
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

        constructor(appType: number, param?: IAppReasonStandard) {
            let self = this;
            self.appType = appType;
            self.reasonCode = ko.observable(param ? param.reasonCode : null);
            self.dispOrder = ko.observable(param ? param.dispOrder : 0);
            self.reasonTemp = ko.observable(param ? param.reasonTemp : "");
            self.defaultFlg = ko.observable(param ? param.defaultFlg : false);

            self.defaultFlg.subscribe(value => {
                console.log(value);
            })
        }
    }

    class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    function makeIcon(value) {
        if (value == true) {
            return '<i class="icon icon-dot"></i>';
        }
        return '';
    }
}




