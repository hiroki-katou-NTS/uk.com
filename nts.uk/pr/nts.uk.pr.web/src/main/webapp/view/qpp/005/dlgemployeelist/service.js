/* global nts, _, UNKNOWN */

var services = (function () {

    var __employeeList = [
        {code: 'A000000000001', name: '日通　社員1'},
        {code: 'A000000000002', name: '日通　社員2'},
        {code: 'A000000000003', name: '日通　社員3'},
        {code: 'A000000000004', name: '日通　社員4'},
        {code: 'A000000000005', name: '日通　社員5'},
        {code: 'A000000000006', name: '日通　社員6'},
        {code: 'A000000000007', name: '日通　社員7'},
        {code: 'A000000000008', name: '日通　社員8'},
        {code: 'A000000000009', name: '日通　社員9'},
        {code: 'A0000000000010', name: '日通　社員10'}
    ];

    var servicePath = {
    	getEmployeeList: 'getEmployeeList',
    };

    nts.request.ajaxmock().set({
        req: function (path, options) {
            return path === servicePath.getEmployeeList;
        },
        res: function () {
            return {employeeList: __employeeList};
        }
    });

    var services = {};

    services.getEmployeeList = function () {
        var dfd = $.Deferred();

        nts.request.ajax(servicePath.getEmployeeList).done(function (res) {
            dfd.resolve(res);
        });

        return dfd.promise();
    };
   
    return services;
})();
