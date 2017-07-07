module nts.uk.com.view.ccg008.test {
    __viewContext.ready(function() {
        var screenModel = new ScreenModel();
        __viewContext.bind(screenModel); 
    }); 
    
    export class ScreenModel {
        
        constructor() {
            var self = this;  
              
        }
        
        loginScreen() {
            var self = this;
            nts.uk.request.jump("/view/ccg/008/a/index.xhtml", {screen: 'login'});
        }
        
        otherScreen() {
            var self = this;  
            nts.uk.request.jump("/view/ccg/008/a/index.xhtml", {});  
        }
    }
}