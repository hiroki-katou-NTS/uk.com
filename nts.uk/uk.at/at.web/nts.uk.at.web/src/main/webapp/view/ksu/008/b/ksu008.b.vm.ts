module nts.uk.at.ksu008.b {

    const API = {
        getAll: "screen/at/ksu008/b/get-layouts/",
        getOne: "screen/at/ksu008/b/get-layout/",
        registerData: "screen/at/ksu008/b/register",
        updateData: "screen/at/ksu008/b/update",
        deleteData: "screen/at/ksu008/b/delete",
        downloadSystemTemplate: "at/file/form9/report/download-system-template"
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        columns: KnockoutObservableArray<any>;
        items: KnockoutObservableArray<ItemModel>;
        currentCode: KnockoutObservable<string>;

        layoutType: KnockoutObservable<number>;
        layoutCode: KnockoutObservable<string>;
        layoutName: KnockoutObservable<string>;
        useAtr: KnockoutObservable<number>;
        fileName: KnockoutObservable<string>;
        fileId: KnockoutObservable<string>;

        // 様式９の表紙
        cellYear: KnockoutObservable<string>;
        cellMonth: KnockoutObservable<string>;
        cellStartTime: KnockoutObservable<string>;
        cellEndTime: KnockoutObservable<string>;
        cellTitle: KnockoutObservable<string>;
        cellPrintPeriod: KnockoutObservable<string>;

        nursingStaffColumnSetting: ColumnSetting;
        nursingAssistantColumnSetting: ColumnSetting;

        // 様式９の明細設定
        nursingStaffDetailSetting: DetailSetting;
        nursingAssistantDetailSetting: DetailSetting;

        enableDownloadTemplate: KnockoutObservable<boolean> = ko.observable(false);
		updateStatus: boolean = false;

        params: any;

        constructor() {
            super();
            const self = this;
            self.columns = ko.observableArray([
                { headerText: self.$i18n('KSU008_40'), prop: 'code', width: 100 },
                { headerText: self.$i18n('KSU008_41'), prop: 'name', width: 210 },
                { headerText: self.$i18n('KSU008_42'), prop: 'use', width: 70, template: '{{if ${use} == "true" }}<i class="icon icon-78"></i>{{/if}}' },
            ]);
            self.items = ko.observableArray([]);
            self.currentCode = ko.observable(null);

            self.layoutType = ko.observable(null);
            self.layoutCode = ko.observable(null);
            self.layoutName = ko.observable(null);
            self.useAtr = ko.observable(1);
            self.fileName = ko.observable(null);
            self.fileId = ko.observable(null);
            self.cellYear = ko.observable(null);
            self.cellMonth = ko.observable(null);
            self.cellStartTime = ko.observable(null);
            self.cellEndTime = ko.observable(null);
            self.cellTitle = ko.observable(null);
            self.cellPrintPeriod = ko.observable(null);
            self.nursingStaffColumnSetting = new ColumnSetting();
            self.nursingAssistantColumnSetting = new ColumnSetting();
            self.nursingStaffDetailSetting = new DetailSetting();
            self.nursingAssistantDetailSetting = new DetailSetting();

            self.layoutType.subscribe(value => {
                if (value == 1) {
                    // load system fixed layouts
                    self.getAllLayoutSetting(true, self.params ? self.params.layoutCode : null);
                } else if (value == 0) {
                    // load user defined layouts
                    self.getAllLayoutSetting(false, self.params ? self.params.layoutCode : null);
                }
                if (self.params) self.params = null;
            });
            self.currentCode.subscribe(value => {
                self.$errors("clear");
                if (value) {
                    self.$blockui("show");
                    self.$ajax(API.getOne + value).done((layout: ItemModel) => {
                        if (layout) {
                            self.layoutCode(layout.code);
                            self.layoutName(layout.name);
                            self.useAtr(layout.use ? 1 : 0);
                            self.fileName(layout.templateFileName);
                            self.fileId(layout.templateFileId);
                            self.cellYear(layout.cover.cellYear);
                            self.cellMonth(layout.cover.cellMonth);
                            self.cellStartTime(layout.cover.cellStartTime);
                            self.cellEndTime(layout.cover.cellEndTime);
                            self.cellTitle(layout.cover.cellTitle);
                            self.cellPrintPeriod(layout.cover.cellPrintPeriod);
                            self.nursingStaffColumnSetting.setData(layout.nursingTable);
                            self.nursingAssistantColumnSetting.setData(layout.nursingAideTable);
                            self.nursingStaffDetailSetting.setData(layout.nursingTable.detailSetting);
                            self.nursingAssistantDetailSetting.setData(layout.nursingAideTable.detailSetting);
                            self.enableDownloadTemplate(true);
                            _.defer(() => {
                                self.layoutType() == 1 ? $("#B4_2").focus() : $("#B3_3").focus();
                            });
                        } else {
                            self.currentCode(null);
                        }
                    }).fail(error => {
                        self.$dialog.error(error);
                    }).always(() => {
                        self.$blockui("hide");
                    });
                } else {
                    // new mode
                    self.layoutCode(null);
                    self.layoutName(null);
                    self.useAtr(1);
                    self.fileName(null);
                    self.fileId(null);
                    self.cellYear(null);
                    self.cellMonth(null);
                    self.cellStartTime(null);
                    self.cellEndTime(null);
                    self.cellTitle(null);
                    self.cellPrintPeriod(null);
                    self.nursingStaffColumnSetting.reset();
                    self.nursingAssistantColumnSetting.reset();
                    self.nursingStaffDetailSetting.reset();
                    self.nursingAssistantDetailSetting.reset();
                    _.defer(() => {
                        $("#B3_2").focus();
                    });
                    self.enableDownloadTemplate(false);
                }
            });
        }

        created(params: any) {
            const vm = this;
            vm.params = params;
            vm.layoutType(params.isSystemFixed ? 1 : 0);
            $("#B6_1-table").ntsFixedTable({});
            $("#B7_2-table").ntsFixedTable({});
        }

        getAllLayoutSetting(isSystemFixed: boolean, code?: string) {
            const vm = this;
            vm.$blockui("show");
            vm.$ajax(API.getAll + isSystemFixed).done((data: Array<any>) => {
                vm.items(_.sortBy(data, ["code"]));
                if (!_.isEmpty(data)) {
                    if (code) {
                        vm.currentCode() == code ? vm.currentCode.valueHasMutated() : vm.currentCode(code);
                    } else {
                        vm.currentCode() == data[0].code ? vm.currentCode.valueHasMutated() : vm.currentCode(data[0].code);
                    }
                } else {
                    vm.currentCode() == null ? vm.currentCode.valueHasMutated() : vm.currentCode(null);
                }
            }).fail(error => {
                vm.$dialog.error(error);
            }).always(() => {
                vm.$blockui("hide");
                _.defer(() => {
                    $("#file-upload .browser-button").attr('tabindex', 0);
                });
            });
        }

        createNew() {
            const vm = this;
            vm.currentCode() == null ? vm.currentCode.valueHasMutated() : vm.currentCode(null);
        }

        registerData() {
            const vm = this;
            vm.$validate(".nts-input:not(:disabled)").then((valid: boolean) => {
                if (valid) {
                    if (vm.layoutType() == 0 && _.isEmpty(vm.fileId())) {
                        vm.$errors("#file-upload .browser-button", {messageId: "MsgB_2", messageParams: [vm.$i18n("KSU008_50")]});
                        return;
                    }
                    vm.$blockui("show");
                    const command = {
                        code: vm.layoutCode(),
                        name: vm.layoutName(),
                        isSystemFixed: vm.layoutType() == 1,
                        isUse: vm.useAtr() == 1,
                        cover: {
                            cellYear: vm.cellYear(),
                            cellMonth: vm.cellMonth(),
                            cellStartTime: vm.cellStartTime(),
                            cellEndTime: vm.cellEndTime(),
                            cellTitle: vm.cellTitle(),
                            cellPrintPeriod: vm.cellPrintPeriod()
                        },
                        nursingTable: vm.nursingStaffColumnSetting.toStaffSetting(vm.nursingStaffDetailSetting),
                        nursingAideTable: vm.nursingAssistantColumnSetting.toAssistantSetting(vm.nursingAssistantDetailSetting),
                        templateFileId: vm.fileId()
                    };
                    vm.$ajax(vm.currentCode() == null ? API.registerData : API.updateData, command).done(() => {
                        vm.$dialog.info({messageId: "Msg_15"}).then(() => {
							vm.updateStatus = true;
                            vm.getAllLayoutSetting(vm.layoutType() == 1, command.code);
                        });
                    }).fail(error => {
                        vm.$dialog.error(error);
                    }).always(() => {
                        vm.$blockui("hide");
                    });
                }
            });
        }

        deleteData() {
            const vm = this;
            vm.$dialog.confirm({messageId: "Msg_18"}).then(result => {
                if (result === 'yes') {
                    vm.$blockui("show");
                    vm.$ajax(API.deleteData, {code: vm.currentCode()}).done(() => {
                        vm.$dialog.info({messageId: "Msg_16"}).then(() => {
                            let nextCode = null;
                            const currentIdx = _.findIndex(vm.items(), i => i.code == vm.currentCode());
                            if (vm.items().length > 1) {
                                nextCode = currentIdx == vm.items().length - 1 ? vm.items()[currentIdx - 1].code : vm.items()[currentIdx + 1].code;
                            }
                            vm.getAllLayoutSetting(vm.layoutType() == 1, nextCode);
							vm.updateStatus = true;
                        });
                    }).fail(error => {
                        vm.$dialog.error(error);
                    }).always(() => {
                        vm.$blockui("hide");
                    });
                }
            });
        }

        closeDialog(): void {
            const vm = this;
            vm.$window.close(vm.updateStatus ? {code: vm.currentCode()} : null);
        }

        openKsu008C() {
            let vm = this;
            let data = {
                sourceCode: vm.currentCode(),
                sourceName: vm.layoutName()
            };
            vm.$window.modal('/view/ksu/008/c/index.xhtml', data).then((result: any) => {
                if (!_.isNil(result)) {
                    // Focus on code just created
                    vm.params = {layoutCode: result.destinationCode};
                    vm.layoutType() == 0 ? vm.layoutType.valueHasMutated() : vm.layoutType(0);
					vm.updateStatus = true;
                }
            });
        }

        onFileChange() {
            const vm = this;
            vm.fileId(null);
            vm.enableDownloadTemplate(false);
        }

        uploadFinished(fileInfo: any) {
            const vm = this;
            vm.fileId(fileInfo.id);
            vm.$errors("clear", "#file-upload .browser-button");
        }

        downloadTemplate() {
            const vm = this;
            if (vm.layoutType() == 1) {
                // vm.$blockui("show");
                $.fileDownload(
                    "/" + nts.uk.request.WEB_APP_NAME.at + "/webapi/" + API.downloadSystemTemplate + "/" + vm.currentCode(),
                    {
                        successCallback: function(url) {
                            // vm.$blockui("hide");
                        },
                        failCallback: function(responseHtml, url, error) {
                            vm.$dialog.error(error);
                            // vm.$blockui("hide");
                        }
                    }
                );
            } else {
                // vm.$blockui("show");
                nts.uk.request.specials.donwloadFile(vm.fileId()).fail(error => {
                    vm.$dialog.error(error);
                }).always(() => {
                    // vm.$blockui("hide");
                });
            }
        }
    }

    interface ItemModel {
        code: string;
        cover: {
            cellYear: string;
            cellMonth: string;
            cellStartTime: string;
            cellEndTime: string;
            cellTitle: string;
            cellPrintPeriod: string;
        };
        name: string;
        nursingAideTable: any;
        nursingTable: any;
        systemFixed: boolean;
        templateFileId: string;
        templateFileName: string;
        use: boolean;
    }

    class Model {
        selectedRuleCode:any;
        b42SelectedRuleCode:any;
        b54SelectedRuleCode:any;
        form9Code:any;
        form9Name:any;
        cellOutPut:any;
        constructor() {
            var self = this
            self.selectedRuleCode = ko.observable(1);
            self.b42SelectedRuleCode = ko.observable(1);
            self.b54SelectedRuleCode = ko.observable(1);
            self.form9Code = ko.observable("123");
            self.form9Name = ko.observable("test Name");
        }
    }

    class DetailSetting {
        bodyStartRow: KnockoutObservable<number>;
        maxNumerOfPeople: KnockoutObservable<number>;
        rowDate: KnockoutObservable<number>;
        rowDayOfWeek: KnockoutObservable<number>;

        constructor() {
            this.bodyStartRow = ko.observable(null);
            this.maxNumerOfPeople = ko.observable(null);
            this.rowDate = ko.observable(null);
            this.rowDayOfWeek = ko.observable(null);
        }

        reset() {
            this.bodyStartRow(null);
            this.maxNumerOfPeople(null);
            this.rowDate(null);
            this.rowDayOfWeek(null);
        }

        setData(data: any) {
            this.bodyStartRow(data.bodyStartRow);
            this.maxNumerOfPeople(data.maxNumerOfPeople);
            this.rowDate(data.rowDate);
            this.rowDayOfWeek(data.rowDayOfWeek);
        }
    }

    class ColumnSetting {
        license: KnockoutObservable<string>;
        hospitalWardName: KnockoutObservable<string>;
        fullName: KnockoutObservable<string>;
        fullTime: KnockoutObservable<string>;
        shortTime: KnockoutObservable<string>;
        partTime: KnockoutObservable<string>;
        concurrentPost: KnockoutObservable<string>;
        officeWork: KnockoutObservable<string>;
        nightShiftOnly: KnockoutObservable<string>;
        day1StartColumn: KnockoutObservable<string>;

        constructor() {
            this.license = ko.observable(null);
            this.hospitalWardName = ko.observable(null);
            this.fullName = ko.observable(null);
            this.fullTime = ko.observable(null);
            this.shortTime = ko.observable(null);
            this.partTime = ko.observable(null);
            this.concurrentPost = ko.observable(null);
            this.officeWork = ko.observable(null);
            this.nightShiftOnly = ko.observable(null);
            this.day1StartColumn = ko.observable(null);
        }

        reset() {
            this.license(null);
            this.hospitalWardName(null);
            this.fullName(null);
            this.fullTime(null);
            this.shortTime(null);
            this.partTime(null);
            this.concurrentPost(null);
            this.officeWork(null);
            this.nightShiftOnly(null);
            this.day1StartColumn(null);
        }

        setData(data: any) {
            this.license(data.license);
            this.hospitalWardName(data.hospitalWardName);
            this.fullName(data.fullName);
            this.fullTime(data.fullTime);
            this.shortTime(data.shortTime);
            this.partTime(data.partTime);
            this.concurrentPost(data.concurrentPost);
            this.officeWork(data.officeWork);
            this.nightShiftOnly(data.nightShiftOnly);
            this.day1StartColumn(data.day1StartColumn);
        }

        toStaffSetting(detailSetting: DetailSetting): any {
            return {
                fullName: this.fullName(),
                day1StartColumn: this.day1StartColumn(),
                detailSetting: ko.toJS(detailSetting),
                license: this.license(),
                hospitalWardName: this.hospitalWardName(),
                fullTime: this.fullTime(),
                shortTime: this.shortTime(),
                partTime: this.partTime(),
                concurrentPost: this.concurrentPost(),
                nightShiftOnly: this.nightShiftOnly()
            }
        }

        toAssistantSetting(detailSetting: DetailSetting): any {
            return {
                fullName: this.fullName(),
                day1StartColumn: this.day1StartColumn(),
                detailSetting: ko.toJS(detailSetting),
                hospitalWardName: this.hospitalWardName(),
                fullTime: this.fullTime(),
                shortTime: this.shortTime(),
                partTime: this.partTime(),
                concurrentPost: this.concurrentPost(),
                officeWork: this.officeWork(),
                nightShiftOnly: this.nightShiftOnly()
            }
        }
    }
}


