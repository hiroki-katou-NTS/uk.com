module nts.uk.at.view.ksu001.jd {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import alertError = nts.uk.ui.dialog.alertError;
    import getText = nts.uk.resource.getText;
    import invisible = nts.uk.ui.block.invisible;
    import clear = nts.uk.ui.block.clear;
    import info = nts.uk.ui.dialog.info;

    export module viewmodel {
        export class ScreenModel {
            //元名称表示
            orgName: KnockoutObservable<string> = ko.observable('');
            //先名称入力
            desName: KnockoutObservable<string> = ko.observable('');
            itemList: KnockoutObservableArray<Infor> = ko.observableArray([]);
            selectedCode: KnockoutObservable<string> = ko.observable('');
            checked: KnockoutObservable<boolean> = ko.observable(false);
            target: KnockoutObservable<number> = ko.observable(0);
            targetID: KnockoutObservable<string>;
            //ページ元番号
            originalPage: KnockoutObservable<number>;

            constructor() {
                let self = this;

                //target: 会社:2/職場:0/職場グループ:1
                let dataShare = getShared('dataForJD');
                self.target = ko.observable(dataShare.target);
                self.targetID = ko.observable(dataShare.targetID);
                self.originalPage = ko.observable(dataShare.pageNumber);
//                self.selectedCode.subscribe((data) => {
//                    let getName = _.find(self.itemList(), function(o) { return o.page == self.selectedCode(); });
//
//                    if (getName != null && getName.name.split(getText('KSU001_161')).length > 1) {
//                        self.orgName(getName.name.split(getText('KSU001_161'))[1]);
//                    }
//                });
            }

            /**
             * decision
             */
            decision(): void {
                let self = this;

                $(".nts-input").trigger("validate");
                if (nts.uk.ui.errors.hasError()) {
                    return;
                }
                invisible();
                //「会社」の場合
                if (self.target() == 2) {
                    let data = {
                        //ページ元番号
                        originalPage: self.originalPage(),
                        //ページ元番号
                        destinationPage: parseInt(self.selectedCode().split(getText('KSU001_161'))[0]
                            .split(getText('KSU001_110'))[1]),
                        //ページ先名称
                        destinationName: self.desName(),
                        //上書きするか
                        overwrite: self.checked()
                    }
                    service.duplicateComShiftPalet(data).done(() => {
                        self.msgDone();
                    }).fail(function(error) {
                        alertError({ messageId: error.messageId, messageParams: error.parameterIds });
                    }).always(function() {
                        clear();
                    });

                }
                //職場　の場合
                else if (self.target() == 0) {
                    let data = {
                        targetID: self.targetID(),
                        //WORKPLACE:0, WORKPLACE_GROUP1
                        targetUnit: self.target(),

                        //ページ元番号
                        originalPage: self.originalPage(),
                        //ページ元番号
                        destinationPage: parseInt(self.selectedCode().split(getText('KSU001_161'))[0]
                            .split(getText('KSU001_110'))[1]),
                        //ページ先名称
                        destinationName: self.desName(),
                        //上書きするか
                        overwrite: self.checked()
                    }
                    service.duplicateOrgShiftPalet(data).done(() => {
                        self.msgDone();
                    }).fail(function(error) {
                        alertError({ messageId: error.messageId, messageParams: error.parameterIds });
                    }).always(function() {
                        clear();
                    });

                }
                //職場グループ　の場合
                else if (self.target() == 1) {
                    let data = {
                        targetID: self.targetID(),
                        //WORKPLACE:0, WORKPLACE_GROUP1
                        targetUnit: self.target(),

                        //ページ元番号
                        originalPage: self.originalPage(),
                        //ページ元番号
                        destinationPage: parseInt(self.selectedCode().split(getText('KSU001_161'))[0]
                            .split(getText('KSU001_110'))[1]),
                        //ページ先名称
                        destinationName: self.desName(),
                        //上書きするか
                        overwrite: self.checked()
                    }
                    service.duplicateOrgShiftPalet(data).done(() => {
                        self.msgDone();
                    }).fail(function(error) {
                        alertError({ messageId: error.messageId, messageParams: error.parameterIds });
                    }).always(function() {
                        clear();
                    });

                }
            }

            msgDone(): void {
                let self = this;
                setShared("dataFromJD", {
                    page: parseInt(self.selectedCode().split(getText('KSU001_161'))[0]
                        .split(getText('KSU001_110'))[1])
                });
                info({ messageId: "Msg_20" }).then(function() {
                    self.closeDialog();
                });
            }

            /**
             * Close dialog
             */
            closeDialog(): void {
                nts.uk.ui.windows.close();
            }

            startPage(): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();
                invisible();
                //「会社」の場合
                if (self.target() == 2) {
                    service.getShiftPaletteByCompany().done((data) => {
                        self.createitemList(data);
                        self.selectedCode(getText('KSU001_110') + self.originalPage() + getText('KSU001_161'));
                        dfd.resolve();
                    }).fail(function(error) {
                        alertError({ messageId: error.messageId });
                        dfd.reject();
                    }).always(function() {
                        clear();
                    });
                }
                //職場　の場合
                else if (self.target() == 0) {
                    service.getShiftPaletteByWP(self.targetID()).done((data) => {
                        self.createitemList(data);
                        self.selectedCode(getText('KSU001_110') + self.originalPage() + getText('KSU001_161'));
                        dfd.resolve();
                    }).fail(function(error) {
                        alertError({ messageId: error.messageId });
                        dfd.reject();
                    }).always(function() {
                        clear();
                    });
                }
                //職場グループ　の場合
                else if (self.target() == 1) {
                    service.getShiftPaletteByWPG(self.targetID()).done((data) => {
                        self.createitemList(data);
                        self.selectedCode(getText('KSU001_110') + self.originalPage() + getText('KSU001_161'));
                        dfd.resolve();
                    }).fail(function(error) {
                        alertError({ messageId: error.messageId });
                        dfd.reject();
                    }).always(function() {
                        clear();
                    });
                }
                return dfd.promise();
            }

            createitemList(data: any): void {
                let self = this;
                let shiftPaletData = [];
                let page: string;
                self.orgName(_.filter(data, function(o) {
                    return o.page == self.originalPage();
                })[0].name);
                for (let i: number = 1; i <= 10; i++) {
                    if (_.filter(data, function(o) {
                        return o.page == i;
                    }).length == 0) {
                        data.push({ page: i, name: null });
                    }
                }
                let data1 = _.filter(data, function(o) {
                    return o.page !== self.originalPage();
                });
                _.sortBy(data1, 'page').forEach(e => {
                    if (e.name != null) {
                        page = getText('KSU001_110') + e.page + getText('KSU001_161');
                        shiftPaletData.push(new Infor(page, page + e.name));
                    } else {
                        page = getText('KSU001_110') + e.page;
                        shiftPaletData.push(new Infor(page, page));
                    }
                });
                self.itemList(shiftPaletData);
            }

        }

        export class Infor {
            page: string;
            name: string;
            constructor(page: string, name: string) {
                let self = this;
                self.page = page;
                self.name = name;
            }
        }
    }
}