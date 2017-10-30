module nts.uk.com.view.cps009.b.viewmodel {
    import error = nts.uk.ui.errors;
    import text = nts.uk.resource.getText;
    import close = nts.uk.ui.windows.close;
    import dialog = nts.uk.ui.dialog;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;

    export class ViewModel {
        itemInitLst: Array<any> = [];
        currentIdLst: KnockoutObservableArray<any> = ko.observableArray([]);
        itemColumns: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: 'id', key: 'id', width: 100, hidden: true },
            { headerText: text('CPS009_33'), key: 'itemName', width: 200 },
        ]);

        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        categoryName: KnockoutObservable<string> = ko.observable('');
        constructor() {

            let self = this;
            self.roundingRules = ko.observableArray([
                { code: '1', name: ReferenceMethodType.NOSETTING },
                { code: '2', name: ReferenceMethodType.FIXEDVALUE },
                { code: '3', name: ReferenceMethodType.SAMEASLOGIN }
            ]);
            self.selectedRuleCode = ko.observable(1);
        }

        start(): JQueryPromise<any> {

            let self = this,
                dfd = $.Deferred();
            self.itemInitLst = [];
            let param = getShared('CPS009B_PARAM') || { categoryName: '', itemInitLst: [] };
            self.categoryName('会社');
            self.itemInitLst.push(new ItemInitValue('AA', "000", "A", true, false));
            for (let i = 0; i < 8; i++) {
                self.itemInitLst.push(new ItemInitValue(i.toString(), "000" + i.toString(),
                "A" + i.toString(),false,false));
            }
            dfd.resolve();
            return dfd.promise();
        }

        registerItems() {
            let self = this;
            setShared('CPS009B_DATA', {
                refMethodType: self.selectedRuleCode(),
                lstItemSelected: self.currentIdLst()
            });
            close();
        }

        closeDialog() {
            setShared('CPS009B_DATA', null);
            close();
        }
    }

    export class ItemInitValue {
        id: string;
        itemCode: string;
        itemName: string;
        isRequired: boolean;
        isCheckBox: boolean;
        constructor(id: string, itemCode: string,
            itemName: string, isRequired: boolean,
            isCheckBox: boolean) {
            let self = this;
            self.id = id;
            self.itemCode = itemCode;
            self.itemName = itemName;
            self.isRequired = isRequired;
            self.isCheckBox = isCheckBox;
        }
    }
    export enum ReferenceMethodType {
        NOSETTING = '設定なし',
        FIXEDVALUE = '固定値',
        SAMEASLOGIN = 'ログイン者と同じ'
    }
    export class BParam {
        code: string;
        name: string;
        isRequired: boolean;
    }
}