var cmm009;
(function (cmm009) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                getCodeOfDepWP: "basic/organization/getcode",
                getAllDepartment: "basic/organization/getalldepartment",
                getAllDepartmentByHistId: "basic/organization/getalldepbyhistid/",
                getMemoByHistId: "basic/organization/getmemobyhistid/",
                getAllWorkPlace: "basic/organization/getallworkplace",
                getAllWorkPlaceByHistId: "basic/organization/getallwkpbyhistid/",
                getMemoWkpByHistId: "basic/organization/getmemowkpbyhistid/",
                addDepartment: "basic/organization/adddepartment",
                updateDepartment: "basic/organization/updatedepartment",
                updatelistDepartment: "basic/organization/updatedepartment",
                updateEndDate: "basic/organization/updateenddate",
                updateEndDateByHistId: "basic/organization/updateenddatebyhistoryid",
                deleteHistory: "basic/organization/deletehistory",
                updateStartDateandEndDate: "basic/organization/updatestartdateandenddate",
                deleteDep: "basic/organization/deletedep",
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
            function getCodeOfDepWP() {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getCodeOfDepWP)
                    .done(function (res) {
                    dfd.resolve(res);
                }).fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getCodeOfDepWP = getCodeOfDepWP;
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
    })(a = cmm009.a || (cmm009.a = {}));
})(cmm009 || (cmm009 = {}));
//# sourceMappingURL=cmm009.a.service.js.map