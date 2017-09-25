module nts.uk.com.view.cmm011.a {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            findLstWorkPlace: "bs/employee/workplace/config/info/find",
        };
        
        export function findLstWorkPlace(baseDate: Date): JQueryPromise<Array<model.TreeWorkplace>> {
            let dfd = $.Deferred();
            nts.uk.request.ajax(servicePath.findLstWorkPlace,{startDate: baseDate}).done(function(res: Array<model.TreeWorkplace>) {
                let list = _.map(res, function(item) {
                    return new model.TreeWorkplace(item.workplaceId, item.code, item.name, item.hierarchyCode, item.childs);
                });

                dfd.resolve(list);
            });
            return dfd.promise();
        }
        
        /**
        * Model namespace.
        */
        export module model {
            
            export class TreeWorkplace {
                workplaceId: string;
                code: string;
                name: string;
                nodeText: string;
                hierarchyCode: string;
                level: number;
                childs: Array<TreeWorkplace>;

                constructor(workplaceId: string, code: string, name: string, hierarchyCode: string,
                    childs: Array<TreeWorkplace>) {
                    let self = this;
                    self.workplaceId = workplaceId;
                    self.code = code;
                    self.name = name;
                    self.nodeText = code + " " + name;
                    self.hierarchyCode = hierarchyCode;
                    self.level = hierarchyCode.length / 3;
                    self.childs = childs;
                }
            }
        }
    }
}
