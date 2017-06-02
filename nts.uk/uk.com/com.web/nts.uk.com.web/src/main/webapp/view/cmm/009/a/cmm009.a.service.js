var cmm009;
(function (cmm009) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                getAllDepartment: "basic/organization/getalldepartment",
                getAllDepartmentByHistId: "basic/organization/getalldepbyhistid/",
                getMemoByHistId: "basic/organization/getmemobyhistid/",
                addDepartment: "basic/organization/adddepartment",
                updateDepartment: "basic/organization/updatedepartment",
                updatelistDepartment: "basic/organization/updatedepartment",
                updateEndDate: "basic/organization/updateenddate",
                updateEndDateByHistId: "basic/organization/updateenddatebyhistoryid",
                deleteHistory: "basic/organization/deletehistory",
                updateStartDateandEndDate: "basic/organization/updatestartdateandenddate",
                deleteDep: "basic/organization/deletedep",
                getAllHistory: "basic/organization/getAllhistoryDepartment"
            };
            function deleteDepartment(department) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.deleteDep, department).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.deleteDepartment = deleteDepartment;
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
            function addDepartment(department) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.addDepartment, department).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.addDepartment = addDepartment;
            function upDateDepartment(department) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.updateDepartment, department).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.upDateDepartment = upDateDepartment;
            function upDateEndDate(obj) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.updateEndDate, obj).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.upDateEndDate = upDateEndDate;
            function upDateListDepartment(listdepartment) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.updatelistDepartment, listdepartment).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.upDateListDepartment = upDateListDepartment;
            function addListDepartment(listdepartment) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.addDepartment, listdepartment).done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.addListDepartment = addListDepartment;
            /**
            * Get list dapartment tแบกi listHistory[0].
            */
            function getAllDepartment() {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getAllDepartment)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllDepartment = getAllDepartment;
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
             * get list dapartment theo historyID
             */
            function getAllDepartmentByHistId(historyId) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getAllDepartmentByHistId + historyId)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllDepartmentByHistId = getAllDepartmentByHistId;
            /**
             * get Memo theo historyID
             */
            function getMemoByHistId(historyId) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getMemoByHistId + historyId)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getMemoByHistId = getMemoByHistId;
        })(service = a.service || (a.service = {}));
    })(a = cmm009.a || (cmm009.a = {}));
})(cmm009 || (cmm009 = {}));
//# sourceMappingURL=cmm009.a.service.js.map