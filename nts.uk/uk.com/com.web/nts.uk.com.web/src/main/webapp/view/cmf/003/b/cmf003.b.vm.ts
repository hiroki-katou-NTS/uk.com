module nts.uk.com.view.cmf003.b.viewmodel {
    import model = cmf003.share.model;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import block = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;

    export class ScreenModel {
        //wizard
        stepList: Array<NtsWizardStep> = [];
        stepSelected: KnockoutObservable<NtsWizardStep> = ko.observable(null);
        activeStep: KnockoutObservable<number> = ko.observable(0);
        
        //gridlist
        items: KnockoutObservableArray<CategoryModel>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        
        constructor() {
            var self = this;

            //起動する
            self.stepList = [
                { content: '.step-1' },
                { content: '.step-2' },
                { content: '.step-3' }
            ];
            self.stepSelected = ko.observable({ id: 'step-1', content: '.step-1' });
            
            //gridlist
             this.items = ko.observableArray([]);
            
            for(let i = 1; i < 100; i++) {
                this.items.push(new CategoryModel('00' + i, '基本給', "基本給 ", "基本給"));
            }
 
            this.columns = ko.observableArray([
                { headerText: '', key: 'code', width: 100, hidden: true },
                { headerText: getText('CMF003_30'), key: 'name', width: 250 },
                { headerText: getText('CMF003_31'), key: 'period', width: 70 },
                { headerText: getText('CMF003_32'), key: 'range', width: 70 }
            ]);
            
            this.currentCode = ko.observable();
            this.currentCodeList = ko.observableArray([]);
        }
        
        
        selectCategory() {
           modal('../c/index.xhtml').onClosed(() => {
                
            });  
         }
        
    }   
}

class CategoryModel {
    code: string;
    name: string;
    period: string;
    range: string;
    
    constructor(code: string, name: string, period: string, range: string) {
        this.code  = code;
        this.name = name;
        this.period = period;
        this.range   = range;
    }
}