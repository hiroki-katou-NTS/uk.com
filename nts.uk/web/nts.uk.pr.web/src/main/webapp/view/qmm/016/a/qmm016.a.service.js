var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm016;
                (function (qmm016) {
                    /**
                     * Module service.
                     */
                    var service;
                    (function (service) {
                        var path = {
                            loadHistoryByUuid: 'pr/proto/wagetable/find/{0}',
                            loadDemensionList: 'pr/proto/wagetable/demensions',
                            loadElementTypeList: 'pr/proto/wagetable/elements',
                            initWageTable: 'pr/proto/wagetable/init',
                            genitem: 'pr/proto/wagetable/reference/genitem',
                            updateHistory: 'pr/proto/wagetable/update',
                            certificateInfo: 'pr/proto/wagetable/sp/certifies'
                        };
                        /**
                         * Service class.
                         */
                        var Service = (function (_super) {
                            __extends(Service, _super);
                            function Service() {
                                _super.apply(this, arguments);
                            }
                            /**
                             * Load history by uuid.
                             */
                            Service.prototype.loadHistoryByUuid = function (uuid) {
                                return nts.uk.request.ajax(nts.uk.text.format(path.loadHistoryByUuid, uuid));
                            };
                            /**
                             * Load demension list.
                             */
                            Service.prototype.loadDemensionList = function () {
                                return nts.uk.request.ajax(path.loadDemensionList);
                            };
                            /**
                             * Load elements type list.
                             */
                            Service.prototype.loadElementList = function () {
                                return nts.uk.request.ajax(path.loadElementTypeList);
                            };
                            /**
                             * Init wage table.
                             */
                            Service.prototype.initWageTable = function (data) {
                                return nts.uk.request.ajax(path.initWageTable, data);
                            };
                            /**
                             * Update history.
                             */
                            Service.prototype.updateHistory = function (data) {
                                return nts.uk.request.ajax(path.updateHistory, data);
                            };
                            /**
                             * Certificate
                             */
                            Service.prototype.loadCertificate = function () {
                                return nts.uk.request.ajax(path.certificateInfo);
                            };
                            /**
                             * Generate item table.
                             */
                            Service.prototype.genearetItemSetting = function (data) {
                                var dfd = $.Deferred();
                                // Call service.
                                nts.uk.request.ajax(path.genitem, data).done(function (res) {
                                    dfd.resolve(res.elementSettings);
                                }).fail(dfd.fail);
                                // Promise.
                                return dfd.promise();
                            };
                            return Service;
                        }(view.base.simplehistory.service.BaseService));
                        service.Service = Service;
                        /**
                         * Instance.
                         */
                        service.instance = new Service({
                            historyMasterPath: 'pr/proto/wagetable/masterhistory',
                            createHisotyPath: 'pr/proto/wagetable/history/create',
                            deleteHistoryPath: 'pr/proto/wagetable/history/delete',
                            updateHistoryStartPath: 'pr/proto/wagetable/history/update/start'
                        });
                    })(service = qmm016.service || (qmm016.service = {}));
                    /**
                     * Model module.
                     */
                    var model;
                    (function (model) {
                        /**
                         * All Demession.
                         */
                        model.allDemension = [
                            { code: 0, name: '一次元', isCertification: false, isAttendance: false },
                            { code: 1, name: '二次元', isCertification: false, isAttendance: false },
                            { code: 2, name: '三次元', isCertification: false, isAttendance: false },
                            { code: 3, name: '資格', isCertification: true, isAttendance: false },
                            { code: 4, name: '精皆勤手当', isCertification: false, isAttendance: true }
                        ];
                        /**
                         * Normal demension.
                         */
                        model.normalDemension = _.filter(model.allDemension, function (di) { return !di.isCertification && !di.isAttendance; });
                        /**
                         * Special demension.
                         */
                        model.specialDemension = _.filter(model.allDemension, function (di) { return di.isCertification || di.isAttendance; });
                        /**
                         * Demension map.
                         */
                        model.demensionMap = new Array();
                        _.forEach(model.allDemension, function (de) {
                            model.demensionMap[de.code] = de;
                        });
                    })(model = qmm016.model || (qmm016.model = {}));
                })(qmm016 = view.qmm016 || (view.qmm016 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qmm016.a.service.js.map