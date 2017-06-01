var cmm011;
(function (cmm011) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                getAllWorkPlace: "basic/organization/getallworkplace",
                getAllWorkPlaceByHistId: "basic/organization/getallwkpbyhistid/",
                getMemoWkpByHistId: "basic/organization/getmemowkpbyhistid/",
                updateEndDateofWkp: "basic/organization/updateenddateofwkp",
                updateEndDateByHistId: "basic/organization/updateenddatewkpbyhistoryid",
                deleteHistory: "basic/organization/deletehistorywkp",
                updateStartDateandEndDate: "basic/organization/updatestartdateandenddatewkp",
                deleteWorkPLace: "basic/organization/deleteworkplace",
                getAllWorkPLaceByHistId: "basic/organization/getallwkpbyhistid/",
                getMemoWorkPLaceByHistId: "basic/organization/getmemowkpbyhistid/",
                addWorkPlace: "basic/organization/addworkplace",
                updatelistWorkPLace: "basic/organization/updateworkplace",
                getAllHistory: "basic/organization/getAllHistoryWorkPlace"
            };
            function upDateListWorkplace(listworkplace) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.updatelistWorkPLace, listworkplace).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.upDateListWorkplace = upDateListWorkplace;
            // add single WorkPlace
            function addWorkPlace(workplace) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.addWorkPlace, workplace).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.addWorkPlace = addWorkPlace;
            // add list WorkPlace
            function addListWorkPlace(listworkplace) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.addWorkPlace, listworkplace).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.addListWorkPlace = addListWorkPlace;
            /**
             * get list workplace theo historyID
             */
            function getAllWorkPLaceByHistId(historyId) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getAllWorkPLaceByHistId + historyId)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllWorkPLaceByHistId = getAllWorkPLaceByHistId;
            /**
             * get Memo theo historyID
             */
            function getMemoWorkPLaceByHistId(historyId) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getMemoWorkPLaceByHistId + historyId)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getMemoWorkPLaceByHistId = getMemoWorkPLaceByHistId;
            function deleteWorkPalce(workplace) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.deleteWorkPLace, workplace).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.deleteWorkPalce = deleteWorkPalce;
            function upDateStartDateandEndDate(updateYMD) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.updateStartDateandEndDate, updateYMD).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.upDateStartDateandEndDate = upDateStartDateandEndDate;
            function deleteHistory(historyid) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.deleteHistory, historyid).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.deleteHistory = deleteHistory;
            function updateEndDateByHistoryId(historyid) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.updateEndDateByHistId, historyid).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.updateEndDateByHistoryId = updateEndDateByHistoryId;
            function upDateEndDateWkp(obj) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.updateEndDateofWkp, obj).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.upDateEndDateWkp = upDateEndDateWkp;
            /**
             * get list History
             */
            function getAllHistory() {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getAllHistory)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllHistory = getAllHistory;
            /**
           * Get list WorkPLace tแบกi listHistory[0].
           */
            function getAllWorkplace() {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getAllWorkPlace)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllWorkplace = getAllWorkplace;
            /**
            * get list dapartment theo historyID
            */
            function getAllWorkPlaceByHistId(historyId) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getAllWorkPlaceByHistId + historyId)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllWorkPlaceByHistId = getAllWorkPlaceByHistId;
            /*
            * get Memo theo historyID
            */
            function getMemoWkpByHistId(historyId) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getMemoWkpByHistId + historyId)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getMemoWkpByHistId = getMemoWkpByHistId;
        })(service = a.service || (a.service = {}));
    })(a = cmm011.a || (cmm011.a = {}));
})(cmm011 || (cmm011 = {}));
//# sourceMappingURL=cmm011.a.service.js.map