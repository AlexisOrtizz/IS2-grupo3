from django.shortcuts import render
from django.http import HttpResponseRedirect
from django.contrib.auth.decorators import login_required

"""
Se definen vistas para la vista home
"""


def home(request):
    """
    Vista de la pagina de inicio, si el usuario ya se encuentra autenticado,
    se muestra el indice
    :param request: consulta recibida
    :return: redireccion a la pagina index si el usuario esta logueado o a la pagina login
            en caso contrario.
    """
    context = {}
    if request.user.is_authenticated:
        redirect_url = '/index/'
        context['nombre'] = request.user.first_name
    else:
        redirect_url = '/login/'
    return HttpResponseRedirect(redirect_url)


@login_required
def index(request):
    """
    Vista de la pagina de saludo, luego del inicio de sesion
    :param request: consulta recibida
    :return: redireccion a la pagina index
    """
    permisos = request.user.get_nombres_permisos()
    context = {
        'nombre': request.user.first_name,
        'title': 'Home',
        'permisos': permisos
    }
    return render(request, '../templates/accounts/index.html', context)