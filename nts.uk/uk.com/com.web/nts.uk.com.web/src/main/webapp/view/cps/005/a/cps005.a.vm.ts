module nts.uk.com.view.cps005.a {
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import service = nts.uk.com.view.cps005.a.service;

    export module viewmodel {
        export class ScreenModel {
            currentData: KnockoutObservable<DataModel>

            constructor() {
                let self = this,
                    dataModel = new DataModel(null);
                self.currentData = ko.observable(dataModel);
                dataModel.categorySelectCode.subscribe(newCategoryCode => {
                    alert("hello DaiKa");
                });
                
            }

            startPage(): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }

            register() {

            }

            addUpdateData() {

            }

            updateData() {

            }
        }
    }

    export class DataModel {
        categoryList: KnockoutObservableArray<PerInfoCategoryModel> = ko.observableArray([
            { categoryCode: "C01", categoryName: "A1" },
            { categoryCode: "C02", categoryName: "A2" },
            { categoryCode: "C03", categoryName: "A3" },
            { categoryCode: "C04", categoryName: "A4" }
        ]);
        categorySelectCode: KnockoutObservable<string> = ko.observable("C01");
        constructor(data: IData) {
            if (!data) return;
            this.categoryList = ko.observableArray(_.map(data.categoryList, item => { return new PerInfoCategoryModel(item) }));
        }
    }


    export class PerInfoCategoryModel {
        categoryCode: string = "";
        categoryName: string = "";
        constructor(data: IPersonInfoCategory) {
            this.categoryCode = data.categoryCode || "";
            this.categoryName = data.categoryName || "";
        }
    }

    interface IData {
        categoryList: Array<IPersonInfoCategory>;
    }

    interface IPersonInfoCategory {
        categoryCode: string;
        categoryName: string;
    }
}

