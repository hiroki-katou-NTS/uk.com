module cps001.f.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
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
        comboItems: Array<PersonCtg> = [];

        comboColumns = [{ prop: 'name', length: 12 }];


        constructor() {
            let self = this,
                dto: any = getShared('CPS001B_PARAM') || {};

            self.fileId = ko.observable("");
            self.filename = ko.observable("");
            self.fileInfo = ko.observable(null);
            self.accept = ko.observableArray([".png", '.gif', '.jpg', '.jpeg']);
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
            let dataShare: IDataShare = getShared('CPS001F_PARAM') || null;
            var dfdGetData = service.getData("90000000-0000-0000-0000-000000000001");
            var dfdGetInfoCategory = service.getInfoCatagory();

            $.when(dfdGetData, dfdGetInfoCategory).done((datafile: Array<IEmpFileMana>, dataCategory: Array<IPersonCtg>) => {
                _.forEach(datafile, function(item) {
                    self.items.push(new GridItem(item));
                });
                _.forEach(dataCategory, function(item) {
                    self.comboItems.push(new PersonCtg(item));
                });
                dfd.resolve();
            });
            return dfd.promise();
        }


        pushData() {
            let self = this;
            // upload file 
            $("#file-upload").ntsFileUpload({ stereoType: "flowmenu" }).done(function(res) {
                self.fileId(res[0].id);
                // save file to domain EmployeeFileManagement
                if (self.items.length == 0) {
                    service.savedata({
                        sid: '90000000-0000-0000-0000-000000000001',
                        fileid: res[0].id,
                        personInfoCtgId: self.comboItems[0].id,
                        uploadOrder: 1
                    }).done(() => {
                        self.restart();
                        self.filename("");
                    });
                } else {
                    service.savedata({
                        sid: '90000000-0000-0000-0000-000000000001',
                        fileid: res[0].id,
                        personInfoCtgId: self.comboItems[0].id,
                        uploadOrder: ((self.items[self.items.length - 1].uploadOrder) + 1)
                    }).done(() => {
                        self.start().done(() => {
                            self.restart();
                            self.filename("");
                        });
                    });
                }
            }).fail(function(err) {
                nts.uk.ui.dialog.alertError(err);
            });
            setShared('CPS001B_VALUE', {});
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