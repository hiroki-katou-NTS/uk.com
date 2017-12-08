module cas009.b.viewmodel {
    import service = cas009.b.service;
    export class ScreenModel {
        listMenu : KnockoutObservableArray<viewmodel.model.Menu>; 
        constructor() {
            var self = this;
            self.listMenu = ko.observableArray([]);
            
        }

        /** Start Page */
       public  startPage(): any {           
            let self = this;
//            service.getPerMissingMenu().done(function(res){
//                    self.listMenu();
//            });
        }

        public closeDialog(): any{
            let self = this;
            nts.uk.ui.windows.close() ;   
        }  
    }


    export module model {
        export class Menu {
            code: string;
            displayName: string;
            screenId: string;
            programId: string;
            queryString : string;
            
            constructor(code: string, displayName: string, screenId: string, 
                            programId: string, queryString: string) {
                this.code = code;
                this.displayName = displayName;
                this.screenId = screenId;
                this.programId = programId;
                this.queryString = queryString;
            }
       }
    }
}