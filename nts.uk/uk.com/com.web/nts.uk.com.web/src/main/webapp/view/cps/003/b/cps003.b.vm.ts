module cps003.b.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    let __viewContext: any = window['__viewContext'] || {};

    export class ViewModel {
        currentMode: KnockoutObservable<ModelData> = ko.observable(null);
        constructor() {
            let self = this;
            self.pushData();
        }

        pushData() {
            let self = this,
                params = getShared('CPS003B_VALUE');
            self.currentMode(new ModelData(params));
        }

        close() {
            close();
        }
    }

    interface IModelDto {
        categoryId: string;
        categoryName: string;
        systemDate: string;
        mode: number;
    }
    
    class ModelData{
        categoryId: string;
        categoryName: string;
        systemDate: string;
        mode: KnockoutObservable<number> = ko.observable(true);
        selectedRuleCode : KnockoutObservable<number> = ko.observable(1);
        roundingRules : Array<any> = [
            { value: 1, name: text("CPS005_55") },
            { value: 2, name: text("CPS005_56") },
        ];
        constructor(data: IModelDto) {
            let self = this;
            self.categoryId = data.categoryId;
            self.categoryName = data.categoryName;
            self.systemDate = data.systemDate;
            self.mode = ko.observable(data.mode);
        }
    }
}