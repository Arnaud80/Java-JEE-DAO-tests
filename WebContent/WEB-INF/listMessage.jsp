<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="us.beans.Message" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP Test file</title>
</head>
<body>
  <%@include file="menu.html" %>
  
  <p>Ceci est une page générée depuis une JSP.</p>

  <p>
  	Récupération du bean :<br/><br/>
  	
  	GET getvar : ${param.getvar}<br/>
  	Nb de message = ${sessionScope.messages.size()}<br/><br/>
  	
  	<c:forEach var="item" items="${sessionScope.messages}" varStatus="loop" >
  		Message #<c:out value="${item.id}" /><br/>
  		Author : <c:out value="${item.author}" /><br/>
  		Message : <c:out value="${item.message}" /><br/>
  		Creation date : <c:out value="${item.creationDate}" /><br/><br/>
  	</c:forEach>
  </p>
  <div>
       <form method="get" action="addMessage">
           <fieldset>
               <legend>Informations client</legend>

               <label for="author">Author <span class="requis">*</span></label>
               <input type="text" id="author" name="author" value="" size="20" maxlength="20" />
               <br />
               
               <label for="message">Message <span class="requis">*</span></label>
               <input type="text" id="message" name="message" value="" size="20" maxlength="20" />
               <br />
               
           </fieldset>
           <input type="submit" value="Save"  />
           <input type="reset" value="Reset" /> <br />
       </form>
   </div>
</body>
</html>