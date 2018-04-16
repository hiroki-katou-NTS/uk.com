module nts.uk.at.view.kmk013.l {
    export module service {
        let paths: any = {
            find: "at/record/workrecord/temporarywork/find",
            save: "at/record/workrecord/temporarywork/save"
        }
        
        export function save(obj: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.save, obj);
        }
        
        export function find(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.find);
        }

    }
}


