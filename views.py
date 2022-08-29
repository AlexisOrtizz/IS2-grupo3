from django.views.generic import ListView, CreateView, UpdateView, DetailView
from rol.forms import *
from django.contrib.messages.views import SuccessMessageMixin
from django.contrib.auth.decorators import login_required
from django.utils.decorators import method_decorator
from django.contrib.auth.mixins import LoginRequiredMixin
from django.http import HttpResponseRedirect


@method_decorator(login_required, name='dispatch')
class RolListView(LoginRequiredMixin, ListView):
    """
    Clase de la vista de la lista de Roles
    """
    template_name = 'rol/list.html'
    model = Rol
    queryset = Rol.objects.all()

    def get(self,request,*args,**kwargs):
        """
        Metodo que es ejecutado al darse una consulta GET
        :param request: consulta recibida
        :param args: argumentos adicionales
        :param kwargs: diccionario de datos adicionales
        :return: la respuesta a la consulta GET
        """
        self.object_list = Rol.objects.all()
        permisos = request.user.get_nombres_permisos()
        return self.render_to_response(self.get_context_data(object_list=self.object_list, permisos=permisos))

    def get_context_data(self, **kwargs):
        """
        Metodo que retorna un diccionario utilizado para pasar datos a las vistas
        :param kwargs: Diccionario de datos adicionales para el contexto
        :return: diccionario de contexto necesario para la correcta visualizacion de los datos
        """
        context = super().get_context_data(**kwargs)
        context['title'] = "Administracion de Roles"
        context['direccion'] = {}
        context['direccion']['Roles'] = (1,'/roles/')
        return context


@method_decorator(login_required, name='dispatch')
class CreateRolView(SuccessMessageMixin, LoginRequiredMixin, CreateView):
    """
    Clase de la vista para la creacion de un nuevo Rol
    """
    template_name = 'rol/rol.html'
    model = Rol
    success_url = '/roles/rol_list'
    form_class = CreateRolForm
    success_message = 'Se ha creado el rol'

    def get(self,request,*args,**kwargs):
        """
        Metodo que es ejecutado al darse una consulta GET
        :param request: consulta recibida
        :param args: argumentos adicionales
        :param kwargs: diccionario de datos adicionales
        :return: la respuesta a la consulta GET
        """
        self.object = None
        formclass = self.get_form_class()
        form = self.get_form(formclass)
        permisos = request.user.get_nombres_permisos()
        return self.render_to_response(self.get_context_data(form=form, permisos=permisos))

    def get_context_data(self, **kwargs):
        """
        Metodo que retorna un diccionario utilizado para pasar datos a las vistas
        :param kwargs: Diccionario de datos adicionales para el contexto
        :return: diccionario de contexto necesario para la correcta visualizacion de los datos
        """
        context = super().get_context_data(**kwargs)
        context['title'] = "Crear Rol"
        return context

    def post(self, request, *args, **kwargs):
        """
        Metodo que es ejecutado al darse una consulta POST
        :param request: consulta recibida
        :param args: argumentos adicionales
        :param kwargs: diccionario de datos adicionales
        :return: la respuesta a la consulta POST
        """
        self.object = None
        formclass = self.get_form_class()
        form = self.get_form(formclass)
        permisos = request.user.get_nombres_permisos()
        if form.is_valid():
            self.object = form.save()
            return HttpResponseRedirect(self.success_url)
        else:
            return self.render_to_response(self.get_context_data(permisos=permisos,form=form))


@method_decorator(login_required, name='dispatch')
class UpdateRolView(LoginRequiredMixin, SuccessMessageMixin, UpdateView):
    """
    Clase de la vista para la modificacion de un Rol
    """
    template_name = 'rol/rol.html'
    model = Rol
    form_class = UpdateRolForm
    success_url = '/roles/rol_list'
    success_message = 'Los cambios se guardaron correctamente'

    def get(self,request,*args,**kwargs):
        """
        Metodo que es ejecutado al darse una consulta GET
        :param request: consulta recibida
        :param args: argumentos adicionales
        :param kwargs: diccionario de datos adicionales
        :return: la respuesta a la consulta GET
        """
        self.object = self.get_object()
        formclass = self.get_form_class()
        form = self.get_form(formclass)
        permisos = request.user.get_nombres_permisos()
        return self.render_to_response(self.get_context_data(form=form, permisos=permisos))

    def get_context_data(self, **kwargs):
        """
        Metodo que retorna un diccionario utilizado para pasar datos a las vistas
        :param kwargs: Diccionario de datos adicionales para el contexto
        :return: diccionario de contexto necesario para la correcta visualizacion de los datos
        """
        context = super().get_context_data(**kwargs)
        context['title'] = "Modificar Rol " + str(self.object.pk)
        context['direccion'] = {}
        context['direccion']['Roles'] = (1, '/roles/')
        context['direccion']['Modificar: '+self.object.nombre] = (2, '/roles/modificar/'+str(self.object.pk)+'/')
        return context

    def get_object(self, queryset=None):
        """
        Metodo que retorna el objeto a ser modificado
        :param queryset:
        :return: Retorna el rol a ser modificado
        """
        return Rol.objects.get(pk=self.kwargs['pk'])

    def post(self, request, *args, **kwargs):
        """
        Metodo que es ejecutado al darse una consulta POST
        :param request: consulta recibida
        :param args: argumentos adicionales
        :param kwargs: diccionario de datos adicionales
        :return: la respuesta a la consulta POST
        """
        self.object = self.get_object()
        formclass = self.get_form_class()
        form = self.get_form(formclass)
        permisos = request.user.get_nombres_permisos()
        if form.is_valid():
            self.object = form.save()
            return HttpResponseRedirect(self.success_url)
        else:
            return self.render_to_response(self.get_context_data(permisos=permisos,form=form))


@method_decorator(login_required, name='dispatch')
class VerRolDetailView(LoginRequiredMixin, SuccessMessageMixin, DetailView):
    """
    Clase de la vista para ver roles
    """
    model = Rol
    template_name = 'rol/ver_rol.html'

    def get(self,request,*args,**kwargs):
        """
        Metodo que es ejecutado al darse una consulta GET
        :param request: consulta recibida
        :param args: argumentos adicionales
        :param kwargs: diccionario de datos adicionales
        :return: la respuesta a la consulta GET
        """
        self.object = self.get_object()
        permisos = request.user.get_nombres_permisos()
        return self.render_to_response(self.get_context_data(permisos=permisos))

    def get_context_data(self, **kwargs):
        """
        Metodo que retorna un diccionario utilizado para pasar datos a las vistas
        :param kwargs: Diccionario de datos adicionales para el contexto
        :return: diccionario de contexto necesario para la correcta visualizacion de los datos
        """
        context = super().get_context_data(**kwargs)
        context['title'] = "Ver Rol "+ str(self.object.pk)
        context['direccion'] = {}
        context['direccion']['Roles'] = (1, '/roles/')
        context['direccion']['Ver: '+self.object.nombre] = (2, '/roles/ver/' + str(self.object.pk) + '/')
        return context

    def get_object(self, queryset=None):
        """
        Metodo que retorna el objeto a ser visualizado
        :param queryset:
        :return: Retorna el rol a ser visualizado
        """
        return Rol.objects.get(pk=self.kwargs['pk'])