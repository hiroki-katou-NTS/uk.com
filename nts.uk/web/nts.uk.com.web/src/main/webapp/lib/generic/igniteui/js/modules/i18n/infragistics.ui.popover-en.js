/*!@license
* Infragistics.Web.ClientUI Popover localization resources 16.1.20161.2145
*
* Copyright (c) 2011-2016 Infragistics Inc.
*
* http://www.infragistics.com/
*
*/

/*global jQuery */
(function($) {
$.ig = $.ig || {};

if (!$.ig.Popover) {
	$.ig.Popover = {};

	$.extend( $.ig.Popover, {
		locale: {
			popoverOptionChangeNotSupported: "Changing the following option after igPopover has been initialized is not supported:",
			popoverShowMethodWithoutTarget: "The target parameter of the show function is mandatory when the selectors option is used"
		}
	});

}
})(jQuery);