module nts.uk.pr.view.qpp._005.f {
    
    export module viewmodel {
        export class ScreenModel {
            increaseHeightOfGrid: KnockoutObservable<string>;
            returnHeightStandardOfGrid: KnockoutObservable<string>;
            
            constructor () {
                var self = this;          
            }
        }
    }
}