<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="bookingForm">
	<div class="span-5">
		<h3>${booking.hotel.name}</h3>
		
		<address>
			${booking.hotel.address}
			<br/>
			${booking.hotel.city}, ${booking.hotel.state}, ${booking.hotel.zip}
			<br/>
			${booking.hotel.country}
		</address>
	</div>
	<div class="span-12 last">
	<c:url var="hotelsUrl" value="/bookingSaved/${booking.hotel.id}" />
	<c:url var="Url" value="/booking/${booking.hotel.id}" />
		<form:form id="confirm" modelAttribute="booking" action="${hotelsUrl}"  method="post">
		<input type="hidden" name="id" value="${booking.hotel.id}" />
		<c:set var="Key1" value="${Url}" />
		<fieldset>
			<legend>Confirm Booking Details</legend>
			<div>
				<div class="span-3">Check In:</div>
				<div class="span-8 last">
					<p><spring:bind path="checkinDate">${status.value}</spring:bind></p>
				</div>
			</div>
			<div>
				<div class="span-3">Checkout:</div>
				<div class="span-8 last">
					<p><spring:bind path="checkoutDate">${status.value}</spring:bind></p>
				</div>
			</div>
	        <div>
	            <div class="span-3">Number of Nights:</div>
	            <div class="span-8 last">
	            	<p><spring:bind path="nights">${status.value}</spring:bind></p>
	            </div>
	        </div>
	        <div>
	            <div class="span-3">Total Payment:</div>
	            <div class="span-8 last">
	            	<p><spring:bind path="total">${status.value}</spring:bind></p>
	            </div>
	        </div>
			<div>
				<div class="span-3">Credit Card #:</div>
				<div class="span-8 last">
					<p>${booking.creditCard}</p>
				</div>
			</div>
			<div>
				 <p>
                    <button type="submit" name="_eventId_confirm">Confirm</button>
                    <button type="submit" name="_eventId_revise" onClick="additionalFunction('${Key1}')" id="revise">Revise</button>
                    <button type="submit" name="_eventId_cancel" onClick="cancelFunction()">Cancel</button>
                    </p>
                    <script type="text/javascript">
                        // <![CDATA[
                        Spring.addDecoration(new Spring.AjaxEventDecoration({elementId:'revise',event:'onclick',formId:'confirm'}));
                        // ]]>
                    </script>

					<script type="text/javascript">
					function additionalFunction(obj){
				
						var form = document.getElementById("confirm");
						var myvar='${Key1}';
							var revisebutton = document
									.getElementsByName("_eventId_revise");
							if (revisebutton) {
								form.action = myvar;
								form.method = "get";
							}
						}
					function cancelFunction() {
						var form = document.getElementById("confirm");
						var cancelbtn = document
								.getElementsByName("_eventId_cancel");
						if (cancelbtn) {
							form.action = "../hotels/search";
							form.method = "get";
						}
					}
					</script>
				</div>
		</fieldset>
		</form:form>
	</div>

</div>
