<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Directory Listing: ${path}</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #dddddd;
        }
    </style>
</head>
<body>
    <h1>Directory Listing: ${path}</h1>
    <#assign format = "dd/MM/yyyy HH:mm:ss">
    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Type</th>
            <th>Size (Bytes)</th>
            <th>Last Modified</th>
            <th>Permissions</th>
        </tr>
        </thead>
        <tbody>
        <#list fileList as file>
            <tr>
                <td>${file.getAbsolutePath()}</td>
                <td>${file.isDirectory()?string("Directory", "File")}</td>
                <td>${file.length()}</td>
                <td>${dateFormat.format(file.lastModified())}</td>
                <td>${file.canRead()?string("r", "-")}${file.canWrite()?string("w", "-")}${file.canExecute()?string("x", "-")}</td>
            </tr>
        </#list>
        </tbody>
    </table>
    <div>
        <#if hasPreviousPage>
            <a href="?path=${path}&page=${currentPage - 1}">Previous Page</a>
        </#if>
        <#if hasNextPage>
            <a href="?path=${path}&page=${currentPage + 1}">Next Page</a>
        </#if>
    </div>
</body>
</html>
