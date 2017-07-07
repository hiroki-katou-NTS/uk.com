module nts.uk.com.view.ccg008.test {
    __viewContext.ready(function() {
        var screenModel = new ScreenModel();
        __viewContext.bind(screenModel); 
    }); 
    
    export class ScreenModel {
        topPageCode: KnockoutObservable<string>;
        
        constructor() {
            var self = this;  
            self.topPageCode = ko.observable("");      
        }
        
        loginScreen() {
            var self = this;
            nts.uk.request.jump("/view/ccg/008/a/index.xhtml", {screen: 'login'});
        }
        
        topPage() {
            var self = this;
            nts.uk.request.jump("/view/ccg/008/a/index.xhtml", {topPageCode: self.topPageCode()});
        }
        
        otherScreen() {
            var self = this;  
            nts.uk.request.jump("/view/ccg/008/a/index.xhtml", {});  
        }
    }
}