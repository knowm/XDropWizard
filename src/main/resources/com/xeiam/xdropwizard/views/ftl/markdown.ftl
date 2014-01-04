<!DOCTYPE html>
<html lang="en">
<head>
<#include "../includes/head.ftl">
</head>

<body>

<div>

    <#include "../includes/header.ftl">

    <div class="markdown">
        <h2>Hardcoded Markdown</h2>
            ${hardcodedhtml}
        <h2>Loaded Markdown</h2>
            ${loadedhtml}
    </div>
    
    <#include "../includes/footer.ftl">

</div>

<#include "../includes/cdn-scripts.ftl">

</body>
</html>