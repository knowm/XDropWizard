<#-- @ftlvariable name="" type="com.xeiam.xdropwizard.views.BookView" -->
<!DOCTYPE html>
<html lang="en">
<head>
<title>Book</title>
<#include "../includes/head.ftl">
</head>

<body>

<div>

    <#include "../includes/header.ftl">

    <div class="markdown">
    <table>
    <tr>
    <td>Book Title:</td><td>${book.title}</td></tr>
    <tr><td>Book Author:</td><td>${book.author}</td></tr>
    <tr><td>Book Price:</td><td>${book.price}</td></tr>
    </table>
    </div>
    
    <#include "../includes/footer.ftl">

</div>

<#include "../includes/cdn-scripts.ftl">

</body>
</html>