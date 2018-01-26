module cps001.f.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import alertError = nts.uk.ui.dialog.alertError;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import showDialog = nts.uk.ui.dialog;
    import permision = service.getCurrentEmpPermision;
    let __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];

    export class ViewModel {


        fileId: KnockoutObservable<string>;
        filename: KnockoutObservable<string>;
        fileInfo: KnockoutObservable<any>;
        textId: KnockoutObservable<string>;
        accept: KnockoutObservableArray<string>;
        asLink: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        fileSize: KnockoutObservable<string>;
        onchange: (filename) => void;
        onfilenameclick: (fileId) => void;

        items: Array<GridItem> = [];

        comboColumns = [{ prop: 'name', length: 12 }];


        constructor() {
            let self = this,
                dto: any = getShared('CPS001F_PARAMS') || {};

            self.fileId = ko.observable("");
            self.filename = ko.observable("");
            self.fileInfo = ko.observable(null);
            self.accept = ko.observableArray([""]);
            self.textId = ko.observable("CPS001_71");
            self.asLink = ko.observable(true);
            self.enable = ko.observable(true);
            self.fileSize = ko.observable("");
            self.onchange = (filename) => {

            };
            self.onfilenameclick = (fileId) => {
                alert(fileId);
            };

        }

        start(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            self.items = [];
            let dataShare: IDataShare = getShared('CPS001F_PARAMS') || null;
            var dfdGetData = service.getData(dataShare.pid);


            $.when(dfdGetData).done((datafile: Array<IEmpFileMana>) => {
                var totalSize = 0;
                _.forEach(datafile, function(item) {
                    totalSize = totalSize + item.originalSize;
                    self.items.push(new GridItem(item));
                });
                if (totalSize != 0) {
                    let sum = (totalSize / 1024).toFixed(2);
                    self.fileSize(nts.uk.resource.getText("CPS001_85", [sum]));
                }
                dfd.resolve();
            });

            return dfd.promise();
        }


        pushData() {
            let self = this,
                dataShare: IDataShare = getShared('CPS001F_PARAMS') || null;
            if (dataShare.pid != null) {

                self.start();

                // upload file
                block();
                $("#file-upload").ntsFileUpload({ stereoType: "document" }).done(function(res) {
                    self.fileId(res[0].id);
                    var fileSize = ((res[0].originalSize) / 1024).toFixed(2);
                    self.fileSize(nts.uk.resource.getText("CPS001_85", [fileSize]));

                    // get Info
                    nts.uk.request.ajax("/shr/infra/file/storage/infor/" + self.fileId()).done(function(info: any) {
                        self.fileInfo(info);

                        // save file to domain EmployeeFileManagement
                        if (self.items.length == 0) {
                            service.savedata({
                                pid: dataShare.pid,
                                fileid: res[0].id,
                                personInfoCtgId: "",
                                uploadOrder: 1
                            }).done(() => {
                                self.restart();
                                self.filename("");
                            });
                        } else {
                            service.savedata({
                                pid: dataShare.pid,
                                fileid: res[0].id,
                                personInfoCtgId: "",
                                uploadOrder: ((self.items[self.items.length - 1].uploadOrder) + 1)
                            }).done(() => {
                                self.start().done(() => {
                                    self.restart();
                                    self.filename("");
                                });
                            });
                        }

                    });

                    unblock();

                }).fail(function(err) {
                    unblock();
                    showDialog.alertError(err);
                });
                setShared('CPS001F_VALUES', {});
            }
        }

        onchangefileupload(filename: string) {
            this.pushData();
        }

        checkSize() {
            var self = this;
            nts.uk.request.ajax("/shr/infra/file/storage/infor/" + self.fileId()).done(function(res) {
                self.fileInfo(res);
            });
        }

        deleteItem(rowItem: IEmpFileMana) {
            let self = this;
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                block();
                service.deletedata(rowItem.fileId).done(() => {
                    unblock();
                    self.restart();
                }).fail((mes) => {
                    unblock();
                });
            }).ifCancel(() => {

            });
        }

        updateCtgItem(rowItem: IEmpFileMana, comboBoxIdNew: any) {
            let self = this;
            if (rowItem.personInfoCategoryId != comboBoxIdNew) {
                service.updateCtgdata({ fileId: rowItem.fileId, personInfoCategoryIdNew: comboBoxIdNew }).done(() => {
                    self.restart();
                });
            }
        }

        restart() {

            __viewContext['viewModel'].start().done(() => {
                init();
                $('.browser-button').focus();
            });
        }

        close() {
            close();
        }


    }

    // Object truyen tu man A sang
    interface IDataShare {
        pid: string;
    }

    interface IPersonAuth {
        roleId: string;
        allowMapUpload: number;
        allowMapBrowse: number;
        allowDocRef: number;
        allowDocUpload: number;
        allowAvatarUpload: number;
        allowAvatarRef: number;
    }


    class GridItem {
        id: string;
        fileName: string;
        fileId: string;
        employeeId: string;
        categoryName: string;
        personInfoCategoryId: string;
        open: string;
        combo: string;
        uploadOrder: number
        constructor(param: IEmpFileMana) {
            this.id = nts.uk.util.randomId();
            this.fileName = param.fileName;
            this.fileId = param.fileId;
            this.employeeId = param.employeeId;
            this.categoryName = param.categoryName;
            this.personInfoCategoryId = param.personInfoCategoryId;
            this.open = param.fileId;
            this.uploadOrder = param.uploadOrder;
            this.combo = param.personInfoCategoryId;
        }
    }

    interface IEmpFileMana {
        employeeId: string;
        fileId: string;
        fileName: string;
        categoryName: string;
        personInfoCategoryId: string;
        uploadOrder: number;
        originalSize: number;
    }

    class EmpFileMana {
        employeeId: string;
        fileId: string;
        categoryName: string;
        personInfoCategoryId: string;
        uploadOrder: number;
        constructor(param: IEmpFileMana) {
            this.employeeId = param.employeeId;
            this.fileId = param.fileId;
            this.categoryName = param.categoryName;
            this.personInfoCategoryId = param.personInfoCategoryId;
            this.uploadOrder = param.uploadOrder;
        }
    }

    interface IPersonCtg {
        id: string;
        categoryCode: string;
        categoryName: string;
        personEmployeeType: number;
        isAbolition: number;
        categoryType: number;
        isFixed: number;
    }

    class PersonCtg {
        id: string;
        name: string;
        constructor(param: IPersonCtg) {
            this.name = param.categoryName;
            this.id = param.id;
        }
    }
}