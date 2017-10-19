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
        comboItems = [new ItemModel('1', '基本給'),
            new ItemModel('2', '役職手当'),
            new ItemModel('3', '基本給2')];

        comboColumns = [{ prop: 'code', length: 4 },
            { prop: 'name', length: 8 }];


        constructor() {
            let self = this,
                dto: IModelDto = getShared('CPS001B_PARAM') || {};
            self.start();
            console.log(self.items);
            $("#grid2").ntsGrid('option', 'dataSource', self.items);

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

        start() {
            let self = this;


            for (let i = 0; i < 5; i++) {
                self.items.push(new GridItem(i));
            }

            service.getInfoCatagory().done((data: Array<IPerInfoCtgFullDto>) => {
                console.log(data);
            });
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
            let fileid = self.fileId();

            setShared('CPS001B_VALUE', {});
        }

        close() {
            close();
        }
    }

    interface IPerInfoCtgFullDto {
        id: string;
        categoryName: string;
    }

    class PerInfoCtgFullDto {
        id: string;
        categoryName: string;
        constructor(param: IPerInfoCtgFullDto) {
            this.id = param.id;
            this.categoryName = param.categoryName;
        }
    }

    class GridItem {
        id: number;
        header2: string;
        flag: boolean;
        ruleCode: string;
        combo: string;
        constructor(index: number) {
            this.id = index;
            this.header2 = index.toString();
            this.flag = index % 2 == 0;
            this.ruleCode = String(index % 3 + 1);
            this.combo = String(index % 3 + 1);
        }
    }

    class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}