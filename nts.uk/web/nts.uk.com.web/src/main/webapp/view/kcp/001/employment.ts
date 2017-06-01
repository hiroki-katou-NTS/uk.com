module kcp001.a.employment {
    export interface Employment {
        code: string;
        name?: string;
        isAlreadySet?: boolean;
    }
    
    export interface EmploymentComponentOption {
        isShowAlreadySet: boolean;
        isMultiSelect: boolean;
    }
    
    export class EmploymentComponentScreenModel {
        option: EmploymentComponentOption;
        employmentList: KnockoutObservableArray<Employment>;
        
        public init($input: JQuery, data: EmploymentComponentOption) {
            // Fake data.
            //data.employmentList()
            
            // Init component.
            $input.appendTo($('body'))
                .load(nts.uk.request.location.appRoot.rawUrl + 'share/component/employee/emplist.xhtml', function() {
                    ko.applyBindings(data, this);
                })
        }
        
        public findEmploymentList() : JQueryPromise<void> {
            var dfd = $.Deferred<any>();
            
            return dfd.promise(); 
        }
        
    }
    
    export module service {
        
        // Service paths.
        var servicePath = {
            findEmployments: '???'
        }
        
        export function findEmployments(){}
        
    }
}