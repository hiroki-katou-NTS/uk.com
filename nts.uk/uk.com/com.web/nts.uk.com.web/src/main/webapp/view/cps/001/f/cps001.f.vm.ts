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
        onfilenameclick: (filename) => void;

        items: Array<GridItem> = [];
        comboItems: Array<PersonCtg> = [];

        comboColumns = [{ prop: 'categoryName', length: 12 }];


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
            self.onfilenameclick = (filename) => {
                alert(filename);
            };
        }

        start(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();

            var dfdGetData = service.getData("90000000-0000-0000-0000-000000000001");
            var dfdGetInfoCategory = service.getInfoCatagory();

            $.when(dfdGetData, dfdGetInfoCategory).done((data1: Array<IEmpFileMana>, data2: Array<IPersonCtg>) => {
                _.forEach(data1, function(item) {
                    self.items.push(new GridItem(item));
                });
                _.forEach(data2, function(item) {
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
                service.savedata({ sid: '90000000-0000-0000-0000-000000000001', fileid: res[0].id }).done(() => {
                    console.log("done");
                });

            }).fail(function(err) {
                nts.uk.ui.dialog.alertError(err);
            });

            setShared('CPS001B_VALUE', {});
        }

        close() {
            close();
        }
    }


    class GridItem {
        id: string;
        filename: string;
        flag: boolean;
        ruleCode: string;
        combo: string;
        constructor(param: IEmpFileMana) {
            this.id = nts.uk.util.randomId();
            this.filename = param.categoryName;
            this.flag = true;
            this.ruleCode = param.fileId;
            this.combo = param.fileId;
        }
    }

    interface IEmpFileMana {
        employeeId: string;
        fileId: string;
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
        code: string;
        name: string;
        constructor(param: IPersonCtg) {
            this.name = param.categoryName;
            this.code = param.id;
        }
    }
}