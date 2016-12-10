/**
 * A custom implementation of the jQuery calendar. Only future dates are shown
 */


$(document).ready(function() {
   $('#txtDate').datepicker({
			     changeMonth: true,
			     changeYear: true,
			     dateFormat: 'mm/yy',
			     minDate: 0,
			       
			     onClose: function() {
			        var iMonth = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
			        var iYear = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
			        $(this).datepicker('setDate', new Date(iYear, iMonth, 1));
			     },
			       
			     beforeShow: function() {
			       if ((selDate = $(this).val()).length > 0) 
			       {
			          iYear = selDate.substring(selDate.length - 4, selDate.length);
			          iMonth = jQuery.inArray(selDate.substring(0, selDate.length - 5), $(this).datepicker('option', 'monthNames'));
			          $(this).datepicker('option', 'defaultDate', new Date(iYear, iMonth, 1));
			           $(this).datepicker('setDate', new Date(iYear, iMonth, 1));
			       }
			    }
			  });
			});
