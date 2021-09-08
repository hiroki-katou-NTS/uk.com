module nts.uk.at.view.kmf002.m {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            startUp: "at/share/holidaymanagement/startUp",
            save: "at/share/holidaymanagement/save",
            getM8_3: "sys/portal/standardmenu/findPgName"
        };

        export function startUp(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.startUp);
            //           let dfd = $.Deferred<any>();
            //           dfd.resolve();
            //           return dfd.promise();
        }

        export function save(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.save, command);


            // let dfd = $.Deferred<any>();
            // dfd.resolve();
            // return dfd.promise();
        }
        export function getM8_3(): JQueryPromise<any> {
            // let namePath =  nts.uk.text.format("sys/portal/standardmenu/findPgName/{0}/{1}/{2}", 'KMK013', 'A', null);
            return nts.uk.request.ajax("com", "sys/portal/standardmenu/findPgName", {
              programId: 'KMK013',
              screenId: 'A',
              queryString: null
            } );
        }
    }
}
