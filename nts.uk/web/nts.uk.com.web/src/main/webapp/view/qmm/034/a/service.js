var qmm034;
(function (qmm034) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                getAllEras: "ctx/basic/era/finderas",
                getEraDetail: "ctx/basic/era/find/{startDate}",
                deleteEra: "ctx/basic/era/deleteData",
                updateEra: "ctx/basic/era/updateData",
                addEra: "ctx/basic/era/addData"
            };
            /**
             * get list era
             */
            function getAllEras() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getAllEras).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllEras = getAllEras;
            /**
             * get a company
             */
            function getEraDetail() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getEraDetail)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getEraDetail = getEraDetail;
            /**
             * add Data
             */
            //     export function addData(layoutMaster: model.EraDto) {
            //        var dfd = $.Deferred<Array<any>>();
            //        nts.uk.request.ajax(paths.addEra, layoutMaster).done(function(res: Array<any>) {
            //         dfd.resolve(res);
            //        }).fail(function(res) {
            //            dfd.reject(res);
            //        })
            //        return dfd.promise();
            //    }
            /**
             * delete Data
             */
            //    export function deleteData(layoutMaster: model.EraDto){
            //        var dfd = $.Deferred<Array<any>>();  
            //        nts.uk.request.ajax(paths.addCompany, layoutMaster).done(function(res: Array<any>){
            //            dfd.resolve(res);    
            //        }).fail(function(res){
            //            dfd.reject(res);
            //        })
            //        return dfd.promise(); 
            //        }
            /**
             * update era
             */
            //    export function updateData(): JQueryPromise<Array<model.EraDto>> {
            //        let dfd = $.Deferred<Array<any>>();
            //        nts.uk.request.ajax(paths.updateEra)
            //            .done(function(res: Array<any>) {
            //                dfd.resolve(res);
            //            })
            //            .fail(function(res) {
            //                dfd.reject(res);
            //            })
            //        return dfd.promise();
            //    }
            function addData(isCreated, command) {
                var dfd = $.Deferred();
                var path = isCreated ? paths.addEra : paths.updateEra;
                nts.uk.request.ajax(path, command)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.addData = addData;
            function deleteData(command) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.deleteEra, command)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.deleteData = deleteData;
            var model;
            (function (model) {
                var EraDto = (function () {
                    function EraDto(eraName, eraMark, startDate, endDate, fixAttribute) {
                        this.eraName = eraName;
                        this.eraMark = eraMark;
                        this.startDate = startDate;
                        this.endDate = endDate;
                        this.fixAttribute = fixAttribute;
                    }
                    return EraDto;
                }());
                model.EraDto = EraDto;
            })(model = service.model || (service.model = {}));
        })(service = a.service || (a.service = {}));
    })(a = qmm034.a || (qmm034.a = {}));
})(qmm034 || (qmm034 = {}));
