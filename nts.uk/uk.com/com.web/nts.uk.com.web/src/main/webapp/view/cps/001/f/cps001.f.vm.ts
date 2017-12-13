module cps001.f.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import alertError = nts.uk.ui.dialog.alertError;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import showDialog = nts.uk.ui.dialog;
    let __viewContext: any = window['__viewContext'] || {};

    export class ViewModel {


        fileId: KnockoutObservable<string>;
        filename: KnockoutObservable<string>;
        fileInfo: KnockoutObservable<any>;
        textId: KnockoutObservable<string>;
        accept: KnockoutObservableArray<string>;
        asLink: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
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
                _.forEach(datafile, function(item) {
                    self.items.push(new GridItem(item));
                });
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
                $("#file-upload").ntsFileUpload({ stereoType: "document" }).done(function(res) {
                    self.fileId(res[0].id);
                    var maxSize = 10485760; // 10MB = 10485760B
                    
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


                }).fail(function(err) {
                    showDialog.alertError(err);
                });
                setShared('CPS001F_VALUE', {});
            }
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
                service.deletedata(rowItem.fileId).done(() => {
                    self.restart();
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
            __viewContext['viewModel'] = new vm.ViewModel();

            __viewContext['viewModel'].start().done(() => {
                init();
                __viewContext.bind(__viewContext['viewModel']);
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