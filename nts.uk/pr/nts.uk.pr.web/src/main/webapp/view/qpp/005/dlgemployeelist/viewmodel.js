/* global services, nts, _, ko, form */

function ScreenModel() {
	var self = this;
	self.employeeList = ko.observable(new EmployeeList());
}
ScreenModel.prototype.start = function() {
	var self = this;
	services.getEmployeeList().done(function(res) {
		var employeeList = _.map(res.employeeList, function(employee) {
			return new Employee(employee.code, employee.name);
		});
		self.employees().items(employeeList);
	});
};

function EmployeeList() {
	var self = this;

	self.items = ko.observableArray([]);
	self.selectedCodes = ko.observable();
}
function Employee(code, name) {
	var self = this;

	self.code = ko.observable(code);
	self.name = ko.observable(name);
}
