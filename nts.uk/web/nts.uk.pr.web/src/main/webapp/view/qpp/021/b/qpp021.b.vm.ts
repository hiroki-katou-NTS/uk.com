module qpp021.b.viewmodel {
    export class ScreenModel {
        stepList: Array<nts.uk.ui.NtsWizardStep>;
        stepSelected: KnockoutObservable<nts.uk.ui.NtsWizardStep>;
        processDateComboBox: KnockoutObservableArray<ComboBoxModel>;
        selectedCbCode: KnockoutObservable<string>;
        selectCategorys: KnockoutObservableArray<RadioBoxModel>;
        selectedRbCode: KnockoutObservable<number>;
        constructor() {
            let self = this;
            self.stepList = [
                { content: '.A_LBL_002-step' },
                { content: '.A_LBL_003-step' },
                { content: '.A_LBL_004-step' },
            ];
            self.stepSelected = ko.observable({ id: 'A_LBL_002-step', content: '.A_LBL_002-step' });
            
            self.processDateComboBox = ko.observableArray([
                new ComboBoxModel('01', '基本給'),
                new ComboBoxModel('02', '役職手当'),
                new ComboBoxModel('03', '基本給'),
                new ComboBoxModel('04', '役職手当'),
                new ComboBoxModel('05', '基本給')
            ]);
            self.selectedCbCode = ko.observable('02');
            
            self.selectCategorys = ko.observableArray([
                new RadioBoxModel(1, '印刷タイプから選択する'),
                new RadioBoxModel(2, '明細レイアウトから選択する'),
            ]);
            self.selectedRbCode = ko.observable(1);
            
        }
        
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        next() {
            $('#wizard').ntsWizard("next");
        }
    }

    class ComboBoxModel {
        cbCode: string;
        cbName: string;
        constructor(cbCode: string, cbName: string) {
            this.cbCode = cbCode;
            this.cbName = cbName;
        }
    }
    
     class RadioBoxModel {
        rbCode: number;
        rbName: string;
        constructor(rbCode: number, rbName: string) {
            this.rbCode = rbCode;
            this.rbName = rbName;
        }
    }
}