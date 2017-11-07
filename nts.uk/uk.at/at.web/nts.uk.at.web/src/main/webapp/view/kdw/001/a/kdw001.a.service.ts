module nts.uk.at.view.kdw001.a {
    export module service {
        let servicePath = {
            findAllClosure: 'ctx/at/shared/workrule/closure/findAll'
        };
       
       export function findAllClosure() {
            //return nts.uk.request.ajax(servicePath.findAllClosure);
           return nts.uk.request.ajax("at",servicePath.findAllClosure);
        }
    }
}
