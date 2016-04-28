<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h1>${hotel.name}</h1>
<address>
	${hotel.address}
	<br />
	${hotel.city}, ${hotel.state}, ${hotel.zip}
	<br />
	${hotel.country}
</address>
<spring:url var="bookUrl" value="/booking/${hotel.id}"/>
						
<form:form modelAttribute="hotel" action="${bookUrl}" method="get">
	<p>
		Nightly Rate:
		<spring:bind path="hotel.price">${hotel.price}</spring:bind>
	</p>
	<input type="hidden" name="hotelId" value="${hotel.id}" />
	<div>
		<button type="submit">Book Hotel</button>
	</div>
</form:form>
