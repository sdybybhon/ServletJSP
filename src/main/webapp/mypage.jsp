<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>File Explorer</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .file-list { max-width: 1000px; }
        .directory { color: #0d6efd; }
        .file { color: #6c757d; }
        .file-size {
            width: 130px;
            text-align: right;
            margin-right: 15px; /* Добавлен пробел */
        }
        .file-date { width: 200px; }
        .drive-buttons { margin-bottom: 15px; }
        .list-group-item.active { font-weight: 600; }
    </style>
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="card shadow">
        <div class="card-header bg-primary text-white">
            <h3 class="mb-0"><i class="fas fa-folder-open"></i> File Explorer</h3>
        </div>

        <div class="card-body">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item">
                        <i class="fas fa-clock"></i>
                        Generated: <fmt:formatDate value="${generatedTime}" pattern="dd.MM.yyyy HH:mm:ss"/>
                    </li>
                </ol>
            </nav>

            <div class="drive-buttons">
                <c:url value="/" var="cDriveUrl">
                    <c:param name="path" value="C:/"/>
                </c:url>
                <a href="${cDriveUrl}" class="btn btn-success">
                    <i class="fas fa-hdd"></i> C: Drive
                </a>

                <c:url value="/" var="dDriveUrl">
                    <c:param name="path" value="D:/"/>
                </c:url>
                <a href="${dDriveUrl}" class="btn btn-success">
                    <i class="fas fa-hdd"></i> D: Drive
                </a>
            </div>

            <!-- Кнопка "Наверх" -->
            <c:if test="${not empty parentPath}">
                <c:url value="/" var="parentUrl">
                    <c:param name="path" value="${parentPath}"/>
                </c:url>
                <a href="${parentUrl}" class="btn btn-outline-primary mb-3">
                    <i class="fas fa-level-up-alt"></i> Up to Parent Directory
                </a>
            </c:if>

            <div class="list-group">
                <div class="list-group-item active d-flex">
                    <div class="flex-grow-1">Name</div>
                    <div class="file-size">Size</div>
                    <div class="file-date">Created</div>
                </div>

                <c:forEach items="${items}" var="item">
                    <c:choose>
                        <c:when test="${item.directory}">
                            <c:url value="/" var="itemUrl">
                                <c:param name="path" value="${currentPath}/${item.name}"/>
                            </c:url>
                            <a href="${itemUrl}" class="list-group-item list-group-item-action directory d-flex">
                                <div class="flex-grow-1">
                                    <i class="fas fa-folder me-2"></i> ${item.name}/
                                </div>
                                <div class="file-size">-</div>
                                <div class="file-date">
                                    <c:if test="${not empty item.creationDate}">
                                        <fmt:formatDate value="${item.creationDate}" pattern="dd.MM.yyyy HH:mm"/>
                                    </c:if>
                                </div>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <c:url value="/download" var="downloadUrl">
                                <c:param name="file" value="${currentPath}/${item.name}"/>
                            </c:url>
                            <a href="${downloadUrl}" class="list-group-item list-group-item-action file d-flex">
                                <div class="flex-grow-1">
                                    <i class="fas fa-file me-2"></i> ${item.name}
                                </div>
                                <div class="file-size">
                                    <c:choose>
                                        <c:when test="${item.size ge 1073741824}">
                                            <fmt:formatNumber value="${item.size/1073741824}" maxFractionDigits="2"/> GB
                                        </c:when>
                                        <c:when test="${item.size ge 1048576}">
                                            <fmt:formatNumber value="${item.size/1048576}" maxFractionDigits="2"/> MB
                                        </c:when>
                                        <c:when test="${item.size ge 1024}">
                                            <fmt:formatNumber value="${item.size/1024}" maxFractionDigits="0"/> KB
                                        </c:when>
                                        <c:when test="${item.size >= 0}">
                                            ${item.size} B
                                        </c:when>
                                        <c:otherwise>
                                            N/A
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="file-date">
                                    <c:choose>
                                        <c:when test="${not empty item.creationDate}">
                                            <fmt:formatDate value="${item.creationDate}" pattern="dd.MM.yyyy HH:mm"/>
                                        </c:when>
                                        <c:otherwise>
                                            -
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
        </div>

        <div class="card-footer text-muted small">
            <i class="fas fa-info-circle"></i> Total items: ${items.size()}
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>