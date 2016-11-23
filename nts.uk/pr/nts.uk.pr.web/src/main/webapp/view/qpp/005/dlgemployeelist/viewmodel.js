/* global services, nts, _, ko, form */

function ScreenModel() {
	var self = this;
	self.employeeList = ko.observable(new EmployeeList());
}
ScreenModel.prototype.start = function() {
	var self = this;
	services.getEmployeeList().done(function(res) {
		var employeeList = _.map(res.employeeList, function(employee) {
			return new Employee(employee.value, employee.text);
		});
		self.employees().items(employeeList);
	});
};

function EmployeeList() {
	var self = this;

	self.items = ko.observableArray([]);
	self.Selected = ko.observable();
}
function Employee(value, name) {
	var self = this;

	self.value = ko.observable(value);
	self.name = ko.observable(name);
}
